package net.slingspot.picks.model.football

import kotlinx.serialization.Serializable

@Serializable
data class Franchise(
    val id: String = "",
    val name: String = "",
    val region: String = "",
    val shortName: String = "",
    val iconUrl: String = ""
)