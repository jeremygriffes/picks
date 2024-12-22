package net.slingspot.picks.util

import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.char

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
