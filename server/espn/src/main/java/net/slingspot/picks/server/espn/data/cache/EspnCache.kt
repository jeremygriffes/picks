package net.slingspot.picks.server.espn.data.cache

import okio.FileSystem

class EspnCache(
    fileSystem: FileSystem
) {
    val seasonTable = SeasonTable(fileSystem)
    val teamTable = TeamTable(fileSystem)
    val weekTable = WeekTable(fileSystem)
    val eventTable = EventTable(fileSystem)

    suspend fun clear() {
        seasonTable.clear()
        teamTable.clear()
        weekTable.clear()
        eventTable.clear()
    }
}
