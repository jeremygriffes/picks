package net.slingspot.picks.server.data

import net.slingspot.picks.model.entities.identity.User

interface DataAccess {
    fun users(): List<User>
}
