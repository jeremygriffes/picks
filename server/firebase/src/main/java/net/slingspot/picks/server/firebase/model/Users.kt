package net.slingspot.picks.server.firebase.model

import net.slingspot.picks.model.entities.identity.User
import net.slingspot.picks.server.firebase.string

fun Map<String, Any>.toUser() = User(
    id = string("id").orEmpty(),
    name = string("name").orEmpty(),
    email = string("email").orEmpty()
)
