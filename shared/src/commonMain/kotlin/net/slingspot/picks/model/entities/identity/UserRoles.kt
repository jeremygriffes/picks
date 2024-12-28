package net.slingspot.picks.model.entities.identity

import kotlinx.serialization.Serializable

@Serializable
data class UserRoles(
    val user: User,
    val roles: List<Role>
)