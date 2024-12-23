package net.slingspot.server.nfl

import kotlinx.datetime.Instant
import net.slingspot.picks.model.football.Contest

interface Contests {
    suspend fun scoresAt(instant: Instant): Set<Contest>
}
