package net.slingspot.picks.server.espn.model

sealed interface EspnModel {
    fun serialize(): String
}
