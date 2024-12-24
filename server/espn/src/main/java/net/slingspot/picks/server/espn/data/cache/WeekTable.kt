package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.server.espn.model.Week

/**
 * Primary key is `startDate`.
 */
interface WeekTable : Crud<String, Week>