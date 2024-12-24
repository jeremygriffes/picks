package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.data.SimpleCrud
import net.slingspot.picks.server.espn.model.Week

class WeekTable : SimpleCrud<String, Week>() {
    override fun keyOf(type: Week) = type.startDate
}