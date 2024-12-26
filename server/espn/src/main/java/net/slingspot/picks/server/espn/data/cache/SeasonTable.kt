package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.data.InMemoryTable
import net.slingspot.picks.server.espn.model.Season

class SeasonTable : InMemoryTable<Int, Season>() {
    override fun keyOf(type: Season) = type.year
}