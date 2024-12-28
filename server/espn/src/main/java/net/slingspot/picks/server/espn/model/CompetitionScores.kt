package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.slingspot.picks.model.Progress
import net.slingspot.picks.model.football.Contest
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
    progress = when (scores.status.type.id) {
        Status.Type.PENDING -> Progress.Pending
        Status.Type.IN_PROGRESS -> Progress.Running
        Status.Type.FINAL -> Progress.Final
        Status.Type.CANCELLED -> Progress.Cancelled
        Status.Type.UNKNOWN -> run {
            println("****** Status.Type.id 4 found: ${scores.status}")
            Progress.Pending
        }

        else -> Progress.Pending
    }
}