package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val id: String,
    val guid: String,
    val location: String,
    val displayName: String,
    val abbreviation: String,
    val color: String,
    val alternateColor: String,
    val isActive: Boolean,
    val logos: List<Logo>
)
