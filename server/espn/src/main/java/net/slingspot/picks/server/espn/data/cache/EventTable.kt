package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.server.espn.model.Event

/**
 * Primary key is `id`.
 */
interface EventTable : Crud<String, Event>