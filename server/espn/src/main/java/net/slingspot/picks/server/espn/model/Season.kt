package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * @param year encompassing the full season, including into the following year (off season)
 * @param type current data of the type (pre-, regular-, post-, or off-season)
 */
@Serializable
data class Season(
    val year: Int,
    val type: Type
) : EspnModel {
    override fun serialize() = Json.encodeToString(this)

    /**
     * @param type part of season: 1=pre, 2=regular, 3=post, 4=off
     * @param name "Regular season" for example
     * @param year which season
     * @param startDate start of this part of the season (see type)
     * @param endDate end of this part of the season (see type)
     * @param weeks references to all of the weeks of this part of the season (see type)
     */
    @Serializable
    data class Type(
        val type: Int,
        val name: String,
        val year: Int,
        val startDate: String,
        val endDate: String,
        val weeks: Ref
    )
}