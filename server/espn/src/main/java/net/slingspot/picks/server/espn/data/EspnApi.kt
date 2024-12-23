package net.slingspot.picks.server.espn.data

import kotlinx.datetime.Instant
import net.slingspot.picks.server.espn.model.Logo
import net.slingspot.picks.server.espn.model.Season

class EspnApi {
    suspend fun season(instant: Instant): Season {
        TODO()
    }

    suspend fun current(): Season {
        TODO()
    }

    suspend fun competition(id: String): Season {
        TODO()
    }

    suspend fun logo(id: String): Logo {
        TODO()
    }

    companion object {
        private const val PATH = "https://sports.core.api.espn.com"
    }
}