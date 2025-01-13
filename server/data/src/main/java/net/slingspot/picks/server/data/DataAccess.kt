package net.slingspot.picks.server.data

import net.slingspot.picks.model.entities.identity.User

interface DataAccess {
    suspend fun users(): List<User>
}
