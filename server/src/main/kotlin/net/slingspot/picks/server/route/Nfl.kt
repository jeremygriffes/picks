package net.slingspot.picks.server.route

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.datetime.Clock
import net.slingspot.picks.server.data.NflRepository
import net.slingspot.picks.util.currentSeason

fun Routing.nfl(nflRepository: NflRepository, clock: Clock) {
    get("/schedule") {
        val schedule = nflRepository.schedule(clock.currentSeason())
        schedule?.let { call.respond(it) } ?: call.response.status(HttpStatusCode.NotFound)
    }

    get("/schedule/{year}") {
        val year = requireNotNull(call.parameters["year"]?.toInt())
        val schedule = nflRepository.schedule(year)
        schedule?.let { call.respond(it) } ?: call.response.status(HttpStatusCode.NotFound)
    }

    get("/contests/today") {
        val contests = nflRepository.today()
        call.respond(contests)
    }

    get("/contests/week") {
        val contests = nflRepository.thisWeek()
        call.respond(contests)
    }

    get("/contests/year/{year}/week/{week}") {
        val year = requireNotNull(call.parameters["year"]?.toInt())
        val week = requireNotNull(call.parameters["week"]?.toInt())
        val contests = nflRepository.week(year, week)
        call.respond(contests)
    }
}
