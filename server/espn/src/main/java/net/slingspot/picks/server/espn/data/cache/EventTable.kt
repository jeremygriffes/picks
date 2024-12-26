package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.data.InMemoryTable
import net.slingspot.picks.server.espn.model.Event

class EventTable : InMemoryTable<String, Event>() {
    override fun keyOf(type: Event) = type.id
}