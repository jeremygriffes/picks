package net.slingspot.picks.server.espn.domain

import io.ktor.client.HttpClient
import kotlinx.datetime.Instant
import net.slingspot.picks.model.football.Contest
import net.slingspot.picks.model.football.Franchise
import net.slingspot.picks.server.espn.data.EspnApi
import net.slingspot.server.nfl.Contests
import net.slingspot.server.nfl.Franchises
import net.slingspot.server.nfl.Schedule

class EspnRepository(
    httpClient: HttpClient
) : Contests, Franchises, Schedule {
    private val api: EspnApi = EspnApi(httpClient)

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
        TODO("Not yet implemented")
    }

}