package net.slingspot.picks.server.espn.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Season(
    val year: Int,
    val startDate: Instant,
    val endDate: Instant,
    val type: Type
) {
    /**
     * `type` appears to be:
     *
     * 1. Preseason
     * 2. Regular season
     * 3. Post season
     *
     * Unclear if type affects the content; post season may not have weeks?
     */
    @Serializable
    data class Type(
        val ref: String,
        val id: String,
        val type: Int,
        val name: String,
        val week: Week,
        val events: List<Event>,
        val weeks: Weeks
    )
}