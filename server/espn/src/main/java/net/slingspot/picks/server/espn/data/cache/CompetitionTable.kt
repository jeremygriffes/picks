package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.data.SimpleCrud
import net.slingspot.picks.server.espn.model.Competition

class CompetitionTable : SimpleCrud<String, Competition>() {
    override fun keyOf(type: Competition) = type.guid
}