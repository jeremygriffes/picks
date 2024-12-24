package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable

@Serializable
data class Competition(
    val id: String,
    val guid: String,
    val date: String,
    val timeValid: Boolean,
    val dateValid: Boolean,
    val competitors: List<Competitor>,
    val status: Ref,
    val odds: Ref
)
