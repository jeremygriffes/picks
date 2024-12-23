package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable

@Serializable
/**
 * @param year encompassing the full season, including into the following year (off season)
 * @param type current data of the type (pre-, regular-, post-, or off-season)
 */
data class Season(
    val year: Int,
    val type: Type
) {
    /**
     * @param startDate start of season, including preseason
     * @param endDate end of season, including off season
     *
     */
    @Serializable
    data class Type(
        val ref: String,
        val type: Int,
        val name: String,
        val year: Int,
        val startDate: String,
        val endDate: String,
        val week: Week,
        val weeks: Ref
    )
}