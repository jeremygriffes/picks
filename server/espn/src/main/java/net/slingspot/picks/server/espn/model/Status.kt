package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val clock: Int,
    val displayClock: String,
    val period: Int,
    val type: Type
) {
    @Serializable
    data class Type(
        val id: String,
        val name: String,
        val state: String,
        val completed: Boolean,
        val description: String,
        val detail: String,
        val shortDetail: String
    )
}
