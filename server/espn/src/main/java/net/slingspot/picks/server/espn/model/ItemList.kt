package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable

/**
 * https://sports.core.api.espn.com/v2/sports/football/leagues/nfl/seasons/2024/types/2/weeks?lang=en&region=us
 */
@Serializable
data class ItemList(
    val count: Int,
    val pageIndex: Int,
    val pageSize: Int,
    val pageCount: Int,
    val items: List<Ref>
)
