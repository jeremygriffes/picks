package net.slingspot.picks.server

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import net.slingspot.picks.Greeting
import net.slingspot.picks.SERVER_PORT
import net.slingspot.picks.server.espn.di.espnModule
import net.slingspot.picks.util.currentSeason
import net.slingspot.server.nfl.NflDataSource
import net.slingspot.server.nfl.di.nflModule
import org.koin.core.context.startKoin

private lateinit var nflDataSource: NflDataSource
private lateinit var clock: Clock

fun main() {
    startKoin {
        modules(espnModule, nflModule)
    }.apply {
        nflDataSource = koin.get<NflDataSource>()
        clock = koin.get<Clock>()
    }

    runBlocking { nflDataSource.initialize(clock.currentSeason()) }

    embeddedServer(CIO, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }

        get("/schedule") {
            val schedule = nflDataSource.schedule(clock.currentSeason())
            schedule?.let { call.respond(it) } ?: call.response.status(HttpStatusCode.NotFound)
        }

        get("/schedule/{year}") {
            val year = requireNotNull(call.parameters["year"]?.toInt())
            val schedule = nflDataSource.schedule(year)
            schedule?.let { call.respond(it) } ?: call.response.status(HttpStatusCode.NotFound)
        }

        get("/contests/today") {
            val contests = nflDataSource.today()
            call.respond(contests)
        }

        get("/contests/week") {
            val contests = nflDataSource.thisWeek()
            call.respond(contests)
        }

        get("/contests/year/{year}/week/{week}") {
            val year = requireNotNull(call.parameters["year"]?.toInt())
            val week = requireNotNull(call.parameters["week"]?.toInt())
            val contests = nflDataSource.week(year, week)
            call.respond(contests)
        }
    }
}
