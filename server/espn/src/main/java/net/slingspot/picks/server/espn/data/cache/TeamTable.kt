package net.slingspot.picks.server.espn.data.cache

import kotlinx.serialization.json.Json
import net.slingspot.picks.server.espn.model.Team
import okio.FileSystem

class TeamTable(fileSystem: FileSystem) : FileStorage<String, Team>(
    fileSystem,
    "team"
) {
    private val withUnknownKeys = Json { ignoreUnknownKeys = true }

    override fun keyOf(type: Team) = type.id
    override fun deserialize(json: String) = withUnknownKeys.decodeFromString<Team>(json)
}