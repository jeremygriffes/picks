package net.slingspot.picks.model.entities.identity

import net.slingspot.picks.model.entities.Player

data class UserPlayers(
    val user: User,
    val players: List<Player>
)
