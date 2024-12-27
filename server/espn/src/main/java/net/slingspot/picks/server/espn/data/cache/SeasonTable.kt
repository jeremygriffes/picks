package net.slingspot.picks.server.espn.data.cache

import kotlinx.serialization.json.Json
import net.slingspot.picks.server.espn.model.Season
import okio.FileSystem

class SeasonTable(fileSystem: FileSystem) : FileStorage<Int, Season>(
    fileSystem,
    "season"
) {
    override fun keyOf(type: Season) = type.year
    override fun deserialize(json: String) = Json.decodeFromString<Season>(json)
}