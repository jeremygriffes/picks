package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.data.InMemoryTable
import net.slingspot.picks.server.espn.model.Week

class WeekTable : InMemoryTable<String, Week>() {
    override fun keyOf(type: Week) = type.startDate
}