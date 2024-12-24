package net.slingspot.server.nfl

import net.slingspot.picks.model.football.Franchise

interface Franchises {
    suspend fun franchisesOf(year: Int): List<Franchise>

}