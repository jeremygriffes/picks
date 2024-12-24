package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.server.espn.model.Team

/**
 * Primary key is `guid`.
 */
interface TeamTable : Crud<String, Team>