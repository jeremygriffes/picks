package net.slingspot.server.nfl

import net.slingspot.picks.model.football.Schedule

interface Schedules {
    suspend fun scheduleOf(year: Int): Schedule
}
