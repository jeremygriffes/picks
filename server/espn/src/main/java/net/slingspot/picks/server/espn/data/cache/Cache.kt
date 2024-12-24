package net.slingspot.picks.server.espn.data.cache

interface Cache {
    val seasonTable: SeasonTable
    val teamTable: TeamTable
    val weekTable: WeekTable
    val eventTable: EventTable
    val competitionTable: CompetitionTable
}