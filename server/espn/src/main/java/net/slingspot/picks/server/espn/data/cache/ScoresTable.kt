package net.slingspot.picks.server.espn.data.cache

import kotlinx.serialization.json.Json
import net.slingspot.picks.server.espn.model.CompetitionScores
import okio.FileSystem

/**
 * Only finalized competitions are persisted. Pending or in-progress data is not persisted.
 */
class ScoresTable(fileSystem: FileSystem) : FileStorage<String, CompetitionScores>(
    fileSystem,
    "competitionScores"
) {
    override fun keyOf(type: CompetitionScores) = type.eventId
    override fun deserialize(json: String) = Json.decodeFromString<CompetitionScores>(json)
}