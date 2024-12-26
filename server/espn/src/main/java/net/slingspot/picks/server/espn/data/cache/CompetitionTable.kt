package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.data.InMemoryTable
import net.slingspot.picks.server.espn.model.Competition

class CompetitionTable : InMemoryTable<String, Competition>() {
    override fun keyOf(type: Competition) = type.guid
}