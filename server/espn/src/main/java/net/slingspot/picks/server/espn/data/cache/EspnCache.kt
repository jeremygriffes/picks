package net.slingspot.picks.server.espn.data.cache

import okio.FileSystem

class EspnCache(
    fileSystem: FileSystem
) {
    val seasonTable = SeasonTable(fileSystem)
    val teamTable = TeamTable(fileSystem)
    val weekTable = WeekTable(fileSystem)
    val eventTable = EventTable(fileSystem)
    val scoresTable = ScoresTable(fileSystem)

    suspend fun eraseEverything() {
        seasonTable.drop()
        teamTable.drop()
        weekTable.drop()
        eventTable.drop()
        scoresTable.drop()
    }
}
