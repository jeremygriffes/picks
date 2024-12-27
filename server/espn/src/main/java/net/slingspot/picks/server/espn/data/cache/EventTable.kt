package net.slingspot.picks.server.espn.data.cache

import kotlinx.serialization.json.Json
import net.slingspot.picks.server.espn.model.Event
import okio.FileSystem
import okio.Path.Companion.toPath

class EventTable(fileSystem: FileSystem) : FileStorage<String, Event>(
    fileSystem,
    "event".toPath()
) {
    override fun keyOf(type: Event) = type.id
    override fun deserialize(json: String) = Json.decodeFromString<Event>(json)
}