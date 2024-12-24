package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.data.SimpleCrud
import net.slingspot.picks.server.espn.model.Season

class SeasonTable : SimpleCrud<Int, Season>() {
    override fun keyOf(type: Season) = type.year
}