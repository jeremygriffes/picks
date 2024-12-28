package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.slingspot.picks.model.Progress
import net.slingspot.picks.model.football.Contest
import net.slingspot.picks.server.espn.model.Status.Type.Companion.CANCELLED
import net.slingspot.picks.server.espn.model.Status.Type.Companion.FINAL
import net.slingspot.picks.server.espn.model.Status.Type.Companion.IN
import net.slingspot.picks.server.espn.model.Status.Type.Companion.IN_PROGRESS
import net.slingspot.picks.server.espn.model.Status.Type.Companion.PENDING
import net.slingspot.picks.server.espn.model.Status.Type.Companion.POST
import net.slingspot.picks.server.espn.model.Status.Type.Companion.PRE
import net.slingspot.picks.model.football.Score as PicksScore

/**
 * Combines status and scores as a convenience; does not otherwise represent any ESPN model.
 */
@Serializable
data class CompetitionScores(
    val eventId: String,
    val status: Status,
    val awayScore: Score,
    val homeScore: Score
) : EspnModel {
    override fun serialize() = Json.encodeToString(this)
}

internal fun Set<Contest>.updateFrom(scores: List<CompetitionScores>): Set<Contest> {
    val untouchedContests = toMutableSet()

    forEach { contest ->
        scores.firstOrNull { it.eventId == contest.id }?.let {
            contest.updateFrom(it)
            untouchedContests.remove(contest)
        }
    }

    return untouchedContests.toSet()
}

internal fun Contest.updateFrom(scores: CompetitionScores) {
    score = PicksScore(
        away = scores.awayScore.value.toInt(),
        home = scores.homeScore.value.toInt()
    )
    val statusType = scores.status.type

    progress = when {
        statusType.id == PENDING -> Progress.Pending
        statusType.id == IN_PROGRESS -> Progress.Running
        statusType.id == FINAL -> Progress.Final
        statusType.id == CANCELLED -> Progress.Cancelled
        statusType.state == PRE -> Progress.Pending
        statusType.state == IN -> Progress.Running
        statusType.state == POST -> Progress.Final
        else -> Progress.Pending
    }
}