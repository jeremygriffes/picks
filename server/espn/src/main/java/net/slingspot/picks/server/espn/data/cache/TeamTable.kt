package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.data.SimpleCrud
import net.slingspot.picks.server.espn.model.Team

class TeamTable : SimpleCrud<String, Team>() {
    override fun keyOf(type: Team) = type.guid
}