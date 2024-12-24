package net.slingspot.picks.server.espn.domain

import kotlinx.datetime.Instant
import net.slingspot.picks.model.football.Contest
import net.slingspot.picks.model.football.Franchise
import net.slingspot.picks.model.football.Schedule
import net.slingspot.picks.server.espn.data.EspnApi
import net.slingspot.picks.server.espn.data.cache.Cache
import net.slingspot.picks.server.espn.data.cache.upsert
import net.slingspot.picks.server.espn.model.Event
import net.slingspot.picks.server.espn.model.ItemList
import net.slingspot.picks.server.espn.model.Week
import net.slingspot.server.nfl.Contests
import net.slingspot.server.nfl.Franchises
import net.slingspot.server.nfl.Schedules

class EspnRepository(
    private val cache: Cache
) : Contests, Franchises, Schedules {
    // TODO inject this.
    private val api = EspnApi()

    override suspend fun scoresAt(instant: Instant): Set<Contest> {
        TODO()
    }

    override suspend fun franchisesOf(year: Int): List<Franchise> {
        TODO()
//        val season = api.(year)
//
//        season.type.week.
    }

    override suspend fun scheduleOf(year: Int): Schedule {
        val season = api.season(year)

        cache.seasonTable.upsert(season)

        // Not sure yet how to handle the type. Pre/regular season weeks are known prior to the
        // season start, but post-season is not know until the regular season ends.
        val weeks = api.getRef<ItemList>(season.type.weeks.ref)

        // TODO consider that this is a paged API, and this code ignores the pages.
        //  Current page size shows that there are 25 items per page, so this will fail when there
        //  are more than 25 weeks in the season.type.
        weeks.items.forEach { weekItem ->
            val week = api.getRef<Week>(weekItem.ref)

            cache.weekTable.upsert(week)

            val events = api.getRef<ItemList>(week.events.ref)

            events.items.forEach { eventItem ->
                val event = api.getRef<Event>(eventItem.ref)

                cache.eventTable.upsert(event)

                event.competitions.forEach {

                }
            }
        }

        TODO()
    }
}