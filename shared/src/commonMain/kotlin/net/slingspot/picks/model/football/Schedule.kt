package net.slingspot.picks.model.football

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val id: String = "",
    val name: String = "",
    val contests: Set<Contest> = emptySet()
)
