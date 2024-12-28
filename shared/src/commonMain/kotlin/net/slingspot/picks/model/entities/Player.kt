package net.slingspot.picks.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: String = "",
    val name: String = "",
)
