package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.data.Crud
import net.slingspot.picks.server.espn.model.Competition
import net.slingspot.picks.server.espn.model.Event
import net.slingspot.picks.server.espn.model.Season
import net.slingspot.picks.server.espn.model.Team
import net.slingspot.picks.server.espn.model.Week

interface Cache {
    val seasonTable: Crud<Int, Season>
    val teamTable: Crud<String, Team>
    val weekTable: Crud<String, Week>
    val eventTable: Crud<String, Event>
    val competitionTable: Crud<String, Competition>

    suspend fun clear()
}
