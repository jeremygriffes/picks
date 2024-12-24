package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.data.SimpleCrud
import net.slingspot.picks.server.espn.model.Event

class EventTable : SimpleCrud<String, Event>() {
    override fun keyOf(type: Event) = type.id
}