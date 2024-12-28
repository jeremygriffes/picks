package net.slingspot.picks.model.entities.identity

import kotlinx.serialization.Serializable
import net.slingspot.picks.model.entities.Player

@Serializable
data class UserPlayers(
    val user: User,
    val players: List<Player>
)
