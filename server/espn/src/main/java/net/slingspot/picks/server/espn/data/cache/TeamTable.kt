package net.slingspot.picks.server.espn.data.cache

import kotlinx.serialization.json.Json
import net.slingspot.picks.server.espn.model.Team
import okio.FileSystem
import okio.Path.Companion.toPath

class TeamTable(fileSystem: FileSystem) : FileStorage<String, Team>(
    fileSystem,
    "team".toPath()
) {
    override fun keyOf(type: Team) = type.guid
    override fun deserialize(json: String) = Json.decodeFromString<Team>(json)
}