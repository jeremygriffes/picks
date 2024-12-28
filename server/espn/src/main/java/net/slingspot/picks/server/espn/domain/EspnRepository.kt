package net.slingspot.picks.server.espn.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.slingspot.picks.data.upsert
import net.slingspot.picks.model.football.Contest
import net.slingspot.picks.model.football.Franchise
import net.slingspot.picks.model.football.Schedule
import net.slingspot.picks.server.espn.data.EspnApi
import net.slingspot.picks.server.espn.data.cache.EspnCache
import net.slingspot.picks.server.espn.data.refresh.periodicallyRefresh
import net.slingspot.picks.server.espn.model.CompetitionScores
import net.slingspot.picks.server.espn.model.Event
import net.slingspot.picks.server.espn.model.Score
import net.slingspot.picks.server.espn.model.Season
import net.slingspot.picks.server.espn.model.Status
import net.slingspot.picks.server.espn.model.Week
import net.slingspot.picks.server.espn.model.timeOf
import net.slingspot.picks.server.espn.model.toFranchise
import net.slingspot.picks.server.espn.model.toSchedule
import net.slingspot.picks.server.espn.model.updateFrom
import net.slingspot.picks.util.currentSeason
import net.slingspot.server.nfl.NflDataSource

internal class EspnRepository(
    private val cache: EspnCache,
    private val api: EspnApi,
    private val clock: Clock,
    private val scope: CoroutineScope
) : NflDataSource {
    private val mutex = Mutex()

    private val franchises = mutableMapOf<Int, Set<Franchise>>()
    private val schedule = mutableMapOf<Int, Schedule>()
    private val eventsByWeek = mutableMapOf<Instant, List<Event>>()
    private val allEvents = mutableListOf<Event>()

    override suspend fun initialize(year: Int, rebuildCache: Boolean) {
        mutex.withLock {
            if (rebuildCache) {
                TODO()
                // cache.eraseEverything()
            }

            val teams = teamsOf(year)
            val season = seasonOf(year)
            val weeks = weeksOf(season.type)
            eventsByWeek.putAll(weeks.associate { timeOf(it.startDate) to eventsOf(it) })
            allEvents.clear()
            allEvents.addAll(
                eventsByWeek.toList().fold(emptyList()) { acc, pair -> acc + pair.second }
            )
            val franchisesThisSeason = teams.map { it.toFranchise() }.toSet()
            val scheduleThisSeason = season.toSchedule(franchisesThisSeason, allEvents)

            franchises[year] = franchisesThisSeason
            schedule[year] = scheduleThisSeason

            allEvents.update(scheduleThisSeason.contests)
        }
    }

    override suspend fun franchises(year: Int) = mutex.withLock {
        franchises[year].orEmpty()
    }

    override suspend fun schedule(year: Int) = mutex.withLock {
        schedule[year]
    }

    override suspend fun update(contest: Contest) {
        mutex.withLock {
            allEvents.firstOrNull { it.id == contest.id }?.update(contest)
        }
    }

    override suspend fun today(): List<Contest> {
        return mutex.withLock {
            val zone = TimeZone.currentSystemDefault()
            val today = clock.now().toLocalDateTime(zone)

            schedule[clock.currentSeason()]?.contests
                ?.filter { today.date == it.scheduledTime.toLocalDateTime(zone).date }
                ?: emptyList()
        }
    }

    override suspend fun thisWeek(): List<Contest> {
        return mutex.withLock {
            val today = clock.now()
            val week = eventsByWeek.keys.minBy { it < today }
            val events = requireNotNull(eventsByWeek[week])

            schedule[clock.currentSeason()]?.contests
                ?.filter { contest -> events.any { it.id == contest.id } }
                ?: emptyList()
        }
    }

    override suspend fun week(year: Int, week: Int): List<Contest> {
        return mutex.withLock {
            // Need to improve the data model so that it's easier to get events by a week number.
            // May want to introduce a Week class with an index and Contest set.
            emptyList()
        }
    }

    private suspend fun teamsOf(year: Int) =
        cache.teamTable.all()
            .takeIf { it.isNotEmpty() }
            ?: api.teams(year).onEach { cache.teamTable.upsert(it) }

    private suspend fun seasonOf(year: Int) =
        cache.seasonTable.get(year)
            ?: api.season(year).also { cache.seasonTable.upsert(it) }

    /**
     * Not sure yet how to handle the type. Pre/regular season weeks are known prior to the
     * season start, but post-season is not know until the regular season ends.
     */
    private suspend fun weeksOf(seasonType: Season.Type) =
        cache.weekTable.weeksIn(seasonType)
            .takeIf { it.isNotEmpty() }
            ?: api.getList<Week>(seasonType.weeks.ref).onEach { cache.weekTable.upsert(it) }

    private suspend fun eventsOf(week: Week) =
        cache.eventTable.eventsIn(week)
            .takeIf { it.isNotEmpty() }
            ?: api.getList<Event>(week.events.ref).onEach { cache.eventTable.upsert(it) }

    private suspend fun List<Event>.update(contests: Set<Contest>) {
        val events = associateBy { it.id }
        val savedScores = cache.scoresTable.all().filter { it.eventId in events.keys }
        val remainingContests = contests.updateFrom(savedScores)

        remainingContests.forEach { contest ->
            events[contest.id]?.let { event ->
                event.update(contest)

                scope.periodicallyRefresh(contest, clock) { event.update(contest) }
            }
        }
    }

    private suspend fun Event.update(contest: Contest) {
        val competition = competitions.firstOrNull() ?: return
        if (clock.now() < timeOf(competition.date)) return

        val (team1, team2) = competition.competitors
        val team1isAway = team1.homeAway == "away"

        val team1Score = api.getRef<Score>(team1.score.ref)
        val team2Score = api.getRef<Score>(team2.score.ref)
        val status = api.getRef<Status>(competition.status.ref)

        val competitionScore = CompetitionScores(
            eventId = id,
            status = status,
            awayScore = if (team1isAway) team1Score else team2Score,
            homeScore = if (team1isAway) team2Score else team1Score
        )

        contest.updateFrom(competitionScore)

        if (status.type.id >= Status.Type.FINAL) {
            cache.scoresTable.upsert(competitionScore)
        }
    }
}