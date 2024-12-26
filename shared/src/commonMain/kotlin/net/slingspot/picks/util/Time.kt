package net.slingspot.picks.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days

val daysOfWeek = DayOfWeekNames(
    "Mon",
    "Tues",
    "Weds",
    "Thurs",
    "Fri",
    "Sat",
    "Sun"
)

val contestDateFormat = DateTimeComponents.Format {
    dayOfWeek(daysOfWeek)
    char('-')
    monthNumber()
    char('/')
    dayOfMonth()
    char('-')
    time(LocalTime.Formats.ISO)
}

fun Clock.currentSeason() =
    (now() - 90.days).toLocalDateTime(TimeZone.currentSystemDefault()).year
