package net.slingspot.picks.server.espn.data.cache

/**
 * The ESPN data source needs only an in-memory cache. There is no good reason to persist the data
 * other than possibly reducing calls to ESPN APIs.
 */
class MemoryCache : Cache {
    override val seasonTable = SeasonTable()
    override val teamTable = TeamTable()
    override val weekTable = WeekTable()
    override val eventTable = EventTable()
    override val competitionTable = CompetitionTable()

    override suspend fun clear() {
        seasonTable.clear()
        teamTable.clear()
        weekTable.clear()
        eventTable.clear()
        competitionTable.clear()
    }
}
