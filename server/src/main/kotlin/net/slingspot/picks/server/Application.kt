package net.slingspot.picks.server

import io.ktor.server.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import net.slingspot.picks.Greeting
import net.slingspot.picks.SERVER_PORT
import net.slingspot.picks.server.di.appModule
import net.slingspot.picks.server.espn.di.espnModule
import net.slingspot.picks.util.currentSeason
import net.slingspot.server.nfl.NflDataSource
import org.koin.core.context.startKoin

private lateinit var nflDataSource: NflDataSource
private lateinit var clock: Clock
private lateinit var scope: CoroutineScope

fun main() {
    startKoin {
        modules(espnModule, appModule)
    }.apply {
        nflDataSource = koin.get<NflDataSource>()
        clock = koin.get<Clock>()
        scope = koin.get<CoroutineScope>()
    }

    runBlocking { nflDataSource.initialize(clock.currentSeason()) }

    // TODO set up alarms to fetch data when a contest begins. While a contest is in progress,
    //  fetch status & scores once every minute. Starting in the 4th quarter, fetch every 20 seconds
    //  until the contest is final.

    embeddedServer(CIO, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }

        get("/schedule") {
            val schedule = nflDataSource.schedule(clock.currentSeason())
            call.respondText(schedule.toString())
        }

        get("/contests/today") {
            val contests = nflDataSource.today()
            call.respondText(contests.joinToString(separator = "\n\n"))
        }
    }
}