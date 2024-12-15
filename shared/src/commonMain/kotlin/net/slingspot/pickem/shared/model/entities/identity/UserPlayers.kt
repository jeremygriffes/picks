package net.slingspot.pickem.shared.model.entities.identity

import net.slingspot.pickem.shared.model.entities.Player

data class UserPlayers(
    val user: User,
    val players: List<Player>
)
