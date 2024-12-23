package net.slingspot.server.nfl

import kotlinx.datetime.Instant

interface Schedule {
    suspend fun scheduleOf(year: Int): Schedule
}
