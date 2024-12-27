package net.slingspot.picks.server.espn.data.cache

import kotlinx.serialization.json.Json
import net.slingspot.picks.server.espn.model.Season
import okio.FileSystem
import okio.Path.Companion.toPath

class SeasonTable(fileSystem: FileSystem) : FileStorage<Int, Season>(
    fileSystem,
    "season".toPath()
) {
    override fun keyOf(type: Season) = type.year
    override fun deserialize(json: String) = Json.decodeFromString<Season>(json)
}