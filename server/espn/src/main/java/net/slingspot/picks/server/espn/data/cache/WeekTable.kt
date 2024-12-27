package net.slingspot.picks.server.espn.data.cache

import kotlinx.serialization.json.Json
import net.slingspot.picks.server.espn.model.Season
import net.slingspot.picks.server.espn.model.Week
import okio.FileSystem

class WeekTable(fileSystem: FileSystem) : FileStorage<String, Week>(
    fileSystem,
    "week"
) {
    override fun keyOf(type: Week) = type.startDate
    override fun deserialize(json: String) = Json.decodeFromString<Week>(json)

    suspend fun weeksIn(seasonType: Season.Type) = all().filter {
        it.startDate >= seasonType.startDate && it.endDate <= seasonType.endDate
    }
}