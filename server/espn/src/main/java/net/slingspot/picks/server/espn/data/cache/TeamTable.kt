package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.data.InMemoryTable
import net.slingspot.picks.server.espn.model.Team

class TeamTable : InMemoryTable<String, Team>() {
    override fun keyOf(type: Team) = type.guid
}