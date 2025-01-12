package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.slingspot.picks.model.football.Contest
import net.slingspot.picks.model.football.Franchise
import net.slingspot.picks.model.football.Schedule
import net.slingspot.picks.server.espn.data.EspnApi

/**
 * @param year encompassing the full season, including into the following year (off season)
 * @param type current data of the type (pre-, regular-, post-, or off-season)
 */
@Serializable
data class Season(
    val year: Int,
    val type: Type
) : EspnModel {
    override fun serialize() = Json.encodeToString(this)

    /**
     * @param type part of season: 1=pre, 2=regular, 3=post, 4=off
     * @param name "Regular season" for example
     * @param year which season
     * @param startDate start of this part of the season (see type)
     * @param endDate end of this part of the season (see type)
     * @param weeks references to all of the weeks of this part of the season (see type)
     */
    @Serializable
    data class Type(
        val type: Int,
        val name: String,
        val year: Int,
        val startDate: String,
        val endDate: String,
        val weeks: Ref
    )
}

/**
 * Maps an ESPN [Season] to a Picks domain model [Schedule].
 */
internal suspend fun Season.toSchedule(
    franchises: Set<Franchise>,
    events: List<Event>,
    api: EspnApi
): Schedule {
    val contests = events.map { event ->
        val (away, home) = event.contestants(franchises, api)

        Contest(
            id = event.id,
            home = home,
            away = away,
            scheduledTime = timeOf(event.date)
        )
    }

    return Schedule(
        id = year.toString(),
        name = type.name,
        contests = contests.toSet()
    )
}

/**
 * @return Pair of `<Away Franchise, Home Franchise>`
 */
private suspend fun Event.contestants(
    franchises: Set<Franchise>,
    api: EspnApi
): Pair<Franchise, Franchise> {
    val competition = competitions.first()
    val (team1, team2) = competition.competitors
    val team1isAway = team1.homeAway == "away"

    // A competitor may be TBD. If so create a franchise instance to match it.
    val team1Franchise = franchises.firstOrNull { it.id == team1.id } ?: team1.toFranchise(api)
    val team2Franchise = franchises.firstOrNull { it.id == team2.id } ?: team2.toFranchise(api)

    return if (team1isAway) team1Franchise to team2Franchise
    else team2Franchise to team1Franchise
}

private suspend fun Competitor.toFranchise(api: EspnApi) = api.getRef<Team>(team.ref).toFranchise()
