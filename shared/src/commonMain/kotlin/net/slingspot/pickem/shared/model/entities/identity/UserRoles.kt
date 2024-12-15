package net.slingspot.pickem.shared.model.entities.identity

data class UserRoles(
    val user: User,
    val roles: List<Role>
)