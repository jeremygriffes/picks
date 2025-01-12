package net.slingspot.picks.server.espn.data.cache

import kotlinx.serialization.json.Json
import net.slingspot.picks.server.espn.model.Season
import net.slingspot.picks.server.espn.model.Week
import net.slingspot.picks.server.espn.model.timeOf
import okio.FileSystem
import kotlin.time.Duration.Companion.days

class WeekTable(fileSystem: FileSystem) : FileStorage<String, Week>(
    fileSystem,
    "week"
) {
    override fun keyOf(type: Week) = type.startDate
    override fun deserialize(json: String) = Json.decodeFromString<Week>(json)

    suspend fun weeksIn(seasonType: Season.Type) = all().filter {
        val seasonStart = timeOf(seasonType.startDate)
        val seasonEnd = timeOf(seasonType.endDate)
        val weekStart = timeOf(it.startDate)

        // Start date of week can be outside of the start date of the season. Compare the week's
        // start date plus a few days to determine whether the week falls within the season.
        val adjustedWeekStart = weekStart + 2.days

        adjustedWeekStart >= seasonStart && weekStart <= seasonEnd
    }
}