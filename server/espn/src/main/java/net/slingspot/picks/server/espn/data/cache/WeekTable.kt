package net.slingspot.picks.server.espn.data.cache

import kotlinx.serialization.json.Json
import net.slingspot.picks.server.espn.model.Week
import okio.FileSystem
import okio.Path.Companion.toPath

class WeekTable(fileSystem: FileSystem) : FileStorage<String, Week>(
    fileSystem,
    "week".toPath()
) {
    override fun keyOf(type: Week) = type.startDate
    override fun deserialize(json: String) = Json.decodeFromString<Week>(json)
}