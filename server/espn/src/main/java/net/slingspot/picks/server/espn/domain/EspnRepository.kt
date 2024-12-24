package net.slingspot.picks.server.espn.domain

import net.slingspot.picks.data.upsert
import net.slingspot.picks.model.football.Contest
import net.slingspot.picks.model.football.Franchise
import net.slingspot.picks.server.espn.data.EspnApi
import net.slingspot.picks.server.espn.data.cache.Cache
import net.slingspot.picks.server.espn.model.Event
import net.slingspot.picks.server.espn.model.ItemList
import net.slingspot.picks.server.espn.model.Team
import net.slingspot.picks.server.espn.model.Week
import net.slingspot.server.nfl.NflDataSource

internal class EspnRepository(
    private val cache: Cache,
    private val api: EspnApi
) : NflDataSource {
    override suspend fun initialize(year: Int) {
        cache.clear()

        val season = api.season(year)

        cache.seasonTable.upsert(season)

        val teams = api.teams(year)

        teams.items.forEach {
            val team = api.getRef<Team>(it.ref)

            cache.teamTable.upsert(team)
        }

        // Not sure yet how to handle the type. Pre/regular season weeks are known prior to the
        // season start, but post-season is not know until the regular season ends.
        val weeks = api.getRef<ItemList>(season.type.weeks.ref)

        weeks.items.forEach { weekItem ->
            val week = api.getRef<Week>(weekItem.ref)

            cache.weekTable.upsert(week)

            val events = api.getRef<ItemList>(week.events.ref)

            events.items.forEach { eventItem ->
                val event = api.getRef<Event>(eventItem.ref)

                cache.eventTable.upsert(event)

                event.competitions.forEach { competition ->
                    cache.competitionTable.upsert(competition)
                }
            }
        }
    }

    override suspend fun franchises(year: Int): Set<Franchise> {
        TODO("Not yet implemented")
    }

    override suspend fun schedule(year: Int): Set<Franchise> {
        TODO("Not yet implemented")
    }

    override suspend fun update(contest: Contest) {
        TODO("Not yet implemented")
    }

}