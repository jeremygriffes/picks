package net.slingspot.picks.server.espn.data.cache

import kotlinx.serialization.json.Json
import net.slingspot.picks.server.espn.model.Event
import net.slingspot.picks.server.espn.model.Week
import okio.FileSystem

class EventTable(fileSystem: FileSystem) : FileStorage<String, Event>(
    fileSystem,
    "event"
) {
    override fun keyOf(type: Event) = type.id
    override fun deserialize(json: String) = Json.decodeFromString<Event>(json)

    suspend fun eventsIn(week: Week) = all().filter {
        it.date >= week.startDate && it.date <= week.endDate
    }
}