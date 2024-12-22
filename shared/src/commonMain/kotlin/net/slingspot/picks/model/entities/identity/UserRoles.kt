package net.slingspot.picks.model.entities.identity

data class UserRoles(
    val user: User,
    val roles: List<Role>
)