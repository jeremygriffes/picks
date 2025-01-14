package net.slingspot.picks.server.data

import net.slingspot.picks.model.entities.identity.User

interface PicksRepository {
    suspend fun users(): List<User>
}
