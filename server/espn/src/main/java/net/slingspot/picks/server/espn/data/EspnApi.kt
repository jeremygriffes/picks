package net.slingspot.picks.server.espn.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import net.slingspot.picks.server.espn.model.Logo
import net.slingspot.picks.server.espn.model.Season

class EspnApi {
    private val http = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                }
            )
        }
    }

    suspend fun season(year: Int): Season {
        http.get(SEASONS + year)
        TODO()
    }

    suspend fun competition(id: String): Season {
        TODO()
    }

    suspend fun logo(id: String): Logo {
        TODO()
    }

    companion object {
        private const val LOCALE = "?lang=en&region=us"
        private const val PATH = "https://sports.core.api.espn.com/"
        private const val FOOTBALL = PATH + "/v2/sports/football/leagues/nfl/"
        private const val SEASONS = FOOTBALL + "seasons/"
    }
}