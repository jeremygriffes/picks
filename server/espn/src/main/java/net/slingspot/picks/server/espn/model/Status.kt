package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val clock: Float,
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
    ) {
        companion object {
            const val PENDING = "1"
            const val IN_PROGRESS = "2"
            const val FINAL = "3"

            // Not sure what id "4" means, haven't seen it yet.
            const val UNKNOWN = "4"
            const val CANCELLED = "5"
        }
    }
}
