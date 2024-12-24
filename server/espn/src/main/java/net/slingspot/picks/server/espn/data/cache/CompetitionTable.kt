package net.slingspot.picks.server.espn.data.cache

import net.slingspot.picks.server.espn.model.Competition

/**
 * Primary key is `guid`.
 */
interface CompetitionTable : Crud<String, Competition>