package net.slingspot.picks.model.entities.identity

import kotlinx.serialization.Serializable

@Serializable
data class Role(
    val id: String,
    val role: String
)