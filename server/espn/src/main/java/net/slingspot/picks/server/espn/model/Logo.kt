package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable

@Serializable
data class Logo(
    val href: String,
    val width: Int,
    val height: Int,
    val alt: String,
    val lastUpdated: String
)
