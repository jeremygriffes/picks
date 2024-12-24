package net.slingspot.picks.server.espn.data.cache

/**
 * The ESPN data source needs only an in-memory cache. There is no good reason to persist the data
 * other than possibly reducing calls to ESPN APIs.
 */
class Cache {
    val seasonTable = SeasonTable()
    val teamTable = TeamTable()
    val weekTable = WeekTable()
    val eventTable = EventTable()
    val competitionTable = CompetitionTable()

    suspend fun clear() {
        seasonTable.clear()
        teamTable.clear()
        weekTable.clear()
        eventTable.clear()
        competitionTable.clear()
    }
}
