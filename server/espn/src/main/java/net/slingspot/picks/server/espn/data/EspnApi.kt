package net.slingspot.picks.server.espn.data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import net.slingspot.picks.server.espn.model.Logo
import net.slingspot.picks.server.espn.model.Season

class EspnApi(private val httpClient: HttpClient) {
    suspend fun season(year: Int): Season {
        httpClient.get(SEASONS + year)
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