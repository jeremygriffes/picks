package net.slingspot.picks.server.espn.model

import kotlinx.datetime.Instant

sealed interface EspnModel {
    fun serialize(): String
}

/**
 * ESPN uses timestamps like "2024-09-06T00:40Z" for events, which is valid ISO 8601.
 * However, kotlinx.datetime.Instant.parse does not accept it without seconds.
 *
 * Try to parse the string as it stands. If it fails, include seconds and reparse.
 */
internal fun timeOf(iso8601: String) = try {
    Instant.parse(iso8601)
} catch (_: Exception) {
    Instant.parse(iso8601.dropLast(1) + ":00Z")
}
