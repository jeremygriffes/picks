package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.server.espn.model.Season

/**
 * Primary key is `year`.
 */
interface SeasonTable : Crud<Int, Season>