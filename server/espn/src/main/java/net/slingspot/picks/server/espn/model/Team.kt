package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.slingspot.picks.model.football.Franchise

@Serializable
data class Team(
    val id: String,
    val location: String,
    val displayName: String,
    val abbreviation: String,
    val color: String? = null,
    val alternateColor: String? = null,
    val isActive: Boolean,
    val logos: List<Logo> = emptyList()
) : EspnModel {
    override fun serialize() = Json.encodeToString(this)
}

fun Team.toFranchise() = Franchise(
    id = id,
    name = displayName,
    region = location,
    shortName = abbreviation,
    iconUrl = logos.firstOrNull()?.href.orEmpty()
)