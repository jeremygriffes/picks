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
import net.slingspot.picks.server.data.PicksRepository
import net.slingspot.picks.server.data.NflRepository
import net.slingspot.picks.server.data.di.dataModule
import net.slingspot.picks.server.espn.di.espnModule
import net.slingspot.picks.server.firebase.di.firebaseModule
import net.slingspot.picks.util.currentSeason
import org.koin.core.context.startKoin

private lateinit var nflRepository: NflRepository
private lateinit var clock: Clock
private lateinit var picksRepository: PicksRepository

fun main() {
    startKoin {
        modules(espnModule, dataModule, firebaseModule)
    }.apply {
        nflRepository = koin.get<NflRepository>()
        clock = koin.get<Clock>()
        picksRepository = koin.get<PicksRepository>()
    }

    runBlocking { nflRepository.initialize(clock.currentSeason(), rebuildCache = false) }

    embeddedServer(CIO, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
        })
    }

    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }

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

        get("/users") {
            val users = picksRepository.users()
            call.respond(users)
        }
    }
}
