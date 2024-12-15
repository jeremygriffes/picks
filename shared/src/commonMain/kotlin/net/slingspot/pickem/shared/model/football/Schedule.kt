package net.slingspot.pickem.shared.model.football

data class Schedule(
    val id: String = "",
    val name: String = "",
    val contests: Set<Contest> = emptySet()
)
