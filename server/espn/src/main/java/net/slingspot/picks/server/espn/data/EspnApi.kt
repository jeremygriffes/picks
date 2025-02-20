package net.slingspot.picks.server.espn.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import net.slingspot.picks.server.espn.model.Competition
import net.slingspot.picks.server.espn.model.ItemList
import net.slingspot.picks.server.espn.model.Score
import net.slingspot.picks.server.espn.model.Season
import net.slingspot.picks.server.espn.model.Status
import net.slingspot.picks.server.espn.model.Team

/**
 * Performs a variety of calls to ESPN sports.core.api.
 */
internal class EspnApi(
    private val http: HttpClient
) {
    /**
     * Follows a ref link, returning the response as T.
     *
     * @see [Ref]
     */
    suspend inline fun <reified T> getRef(refUrl: String) = http.get(refUrl).body<T>()

    /**
     * Reads a all pages of a paged response of Refs, following each ref link, returning a List<T>.
     *
     * @see [ItemList]
     * @see [Ref]
     */
    suspend inline fun <reified T> getList(url: String): List<T> {
        val loaded = mutableListOf<T>()
        var index = 1
        var count = 1

        while (index <= count) {
            val itemList = http.get(url + PAGE + index).body<ItemList>()

            itemList.items.map { loaded.add(getRef(it.ref)) }

            index = itemList.pageIndex + 1
            count = itemList.pageCount
        }

        return loaded.toList()
    }

    suspend fun season(year: Int): Season = http.get(seasonUrl(year)).body()

    suspend fun teams(year: Int): List<Team> = getList(teamsUrl(year))

    suspend fun competition(eventId: Int): Competition = http.get(competitionUrl(eventId)).body()

    suspend fun competitionStatus(eventId: Int): Status =
        http.get(competitionStatusUrl(eventId)).body()

    suspend fun score(eventId: Int, competitorId: Int): Score =
        http.get(scoreUrl(eventId, competitorId)).body()

    companion object {
        private const val NFL = "https://sports.core.api.espn.com/v2/sports/football/leagues/nfl"
        private const val SEASONS = "seasons"
        private const val TYPES = "types"
        private const val WEEKS = "weeks"
        private const val EVENTS = "events"
        private const val COMPETITIONS = "competitions"
        private const val STATUS = "status"
        private const val COMPETITORS = "competitors"
        private const val SCORES = "scores"
        private const val TEAMS = "teams"
        private const val PAGE = "?page="

        // http://sports.core.api.espn.com/v2/sports/football/leagues/nfl/seasons/2024
        private fun seasonUrl(year: Int) = "$NFL/$SEASONS/$year"

        // https://sports.core.api.espn.com/v2/sports/football/leagues/nfl/seasons/2024/teams
        private fun teamsUrl(year: Int) = "${seasonUrl(year)}/$TEAMS"

        // http://sports.core.api.espn.com/v2/sports/football/leagues/nfl/seasons/2024/types/2
        private fun seasonTypeUrl(year: Int, type: Int) = "${seasonUrl(year)}/$TYPES/$type"

        private fun weekUrl(year: Int, type: Int, week: Int) =
            "${seasonTypeUrl(year, type)}/$WEEKS/$week"

        // http://sports.core.api.espn.com/v2/sports/football/leagues/nfl/seasons/2024/types/2/weeks/16
        private fun eventsOfWeekUrl(year: Int, type: Int, week: Int) =
            "${weekUrl(year, type, week)}/$EVENTS"

        // http://sports.core.api.espn.com/v2/sports/football/leagues/nfl/events/401671494
        private fun eventUrl(eventId: Int) = "$NFL/$EVENTS/$eventId"

        // http://sports.core.api.espn.com/v2/sports/football/leagues/nfl/events/401671494/competitions/401671494
        private fun competitionUrl(eventId: Int) = "${eventUrl(eventId)}/$COMPETITIONS/$eventId"

        // http://sports.core.api.espn.com/v2/sports/football/leagues/nfl/events/401671494/competitions/401671494/status
        private fun competitionStatusUrl(eventId: Int) = "${competitionUrl(eventId)}/$STATUS"

        // https://sports.core.api.espn.com/v2/sports/football/leagues/nfl/events/401671717/competitions/401671717/competitors/28/score
        private fun scoreUrl(eventId: Int, competitorId: Int) =
            "${competitionUrl(eventId)}/$COMPETITORS/$competitorId/$SCORES"
    }
}