package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable

@Serializable
data class Events(
    val count: Int,
    val pageIndex: Int,
    val pageSize: Int,
    val pageCount: Int,
    val items: List<Ref>
)