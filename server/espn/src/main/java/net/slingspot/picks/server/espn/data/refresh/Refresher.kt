package net.slingspot.picks.server.espn.data.refresh

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import net.slingspot.picks.model.Progress
import net.slingspot.picks.model.football.Contest
import kotlin.time.Duration.Companion.seconds

private val refreshRate = 30.seconds

fun CoroutineScope.periodicallyRefresh(
    contest: Contest,
    clock: Clock,
    onRefresh: suspend () -> Unit
) {
    suspend fun periodicRefresh() {
        while (contest.progress == Progress.Pending || contest.progress == Progress.Running) {
            onRefresh()
            delay(refreshRate)
        }
    }

    suspend fun setAlarm(time: Instant) {
        delay(time - clock.now())
        periodicRefresh()
    }

    launch {
        when (contest.progress) {
            Progress.Pending -> setAlarm(contest.scheduledTime)
            Progress.Running -> periodicRefresh()
            Progress.Cancelled,
            Progress.Final -> Unit // Nothing to do.
        }
    }
}

