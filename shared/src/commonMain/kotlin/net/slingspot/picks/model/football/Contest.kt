package net.slingspot.picks.model.football

import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.serialization.Serializable
import net.slingspot.picks.model.Progress
import net.slingspot.picks.model.Progress.Cancelled
import net.slingspot.picks.model.Progress.Final
import net.slingspot.picks.model.Progress.Pending
import net.slingspot.picks.util.contestDateFormat
import kotlin.concurrent.Volatile

@Serializable
data class Contest(
    val id: String = "",
    val home: Franchise = Franchise(),
    val away: Franchise = Franchise(),
    val scheduledTime: Instant = Instant.DISTANT_FUTURE
) {
    @Volatile
    var progress: Progress = Pending

    @Volatile
    var score: Score = Score()

    val tied: Boolean get() = score.home == score.away
    val homeLead: Boolean get() = score.home > score.away
    val awayLead: Boolean get() = score.home < score.away
    val total: Int get() = score.home + score.away

    val victors: Set<Franchise>
        get() = if (progress is Final) {
            when {
                tied -> setOf(home, away)
                homeLead -> setOf(home)
                awayLead -> setOf(away)
                else -> emptySet()
            }
        } else emptySet()

    override fun toString() = "Contest(" +
            "id=$id, " +
            "away=${away.shortName}, " +
            "home=${home.shortName}, " +
            "scheduledTime=${scheduledTime.format(contestDateFormat)}, " +
            "score=$score " +
            "progress=$progress)"

    companion object {
        fun Set<Contest>.allPending() = all {
            it.progress == Pending || it.progress == Cancelled
        }

        fun Set<Contest>.allCompleted() = all {
            it.progress == Final || it.progress == Cancelled
        }
    }
}
