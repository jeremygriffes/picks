package net.slingspot.picks.server.espn

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import kotlinx.datetime.Instant
import net.slingspot.picks.model.football.Franchise
import net.slingspot.server.nfl.Franchises

class EspnFranchises(private val client: HttpClient) : Franchises {
    override suspend fun franchisesOf(instant: Instant): List<Franchise> {
        //client.get()
    }
}