package net.slingspot.pickem.server.game

import net.slingspot.pickem.shared.model.Progress.Final
import net.slingspot.pickem.shared.model.Progress.Pending
import net.slingspot.pickem.shared.model.Progress.Running
import net.slingspot.pickem.shared.model.entities.Game
import net.slingspot.pickem.shared.model.entities.GameStatus
import net.slingspot.pickem.shared.model.entities.Picks
import net.slingspot.pickem.shared.model.entities.Player
import net.slingspot.pickem.shared.model.entities.Ranking
import net.slingspot.pickem.shared.model.football.Contest
import net.slingspot.pickem.shared.model.football.Contest.Companion.allCompleted
import net.slingspot.pickem.shared.model.football.Contest.Companion.allPending
import net.slingspot.pickem.shared.model.football.Franchise

private typealias Scores = HashMap<Player, Int>
private typealias PlayersPicks = Map<Player, Picks>

fun Game.calculate(): GameStatus {
    val scores = Scores()
    val overUnder = OverUnder(overUnderContest)
    val finishedContests = contests.filter { it.progress == Final }
    val unfinishedContests = contests.filter { it.progress == Running || it.progress == Pending }

    playersPicks.forEach { playerPicks ->
        val player = playerPicks.key
        val picks = playerPicks.value

        scores[player] = 0

        finishedContests.forEach {
            if (picks.selected(it.victors)) scores.awardPointTo(player)
        }
    }

    val rankings = buildRankings(scores).include(overUnder, playersPicks)

    val contenders = unfinishedContests.potentialOutcomes(scores, playersPicks)

    val gameProgress = when {
        contests.allPending() -> Pending
        contenders.size == 1 -> Final
        contests.allCompleted() -> Final
        else -> Running
    }

    return GameStatus(scores, rankings, contenders, gameProgress)
}

/**
 * Build rankings based solely on player's correct picks (not including over/under number).
 */
private fun buildRankings(scores: Scores): List<Ranking> {
    val rankBuilder = mutableListOf<Ranking>()

    scores.forEach {
        val score = it.value
        val player = setOf(it.key)

        for (index in 0 until rankBuilder.size) {
            val ranking = rankBuilder[index]

            if (score == ranking.score) {
                rankBuilder[index] = Ranking(score, ranking.players + player)
                return@forEach
            } else if (score < ranking.score) {
                rankBuilder.add(index, Ranking(score, player))
                return@forEach
            }
        }
        rankBuilder.add(Ranking(score, player))
    }

    return rankBuilder.sortedByDescending { it.score }
}

/**
 * Determine rankings after including the over/under number.
 */
private fun List<Ranking>.include(
    overUnder: OverUnder,
    playersPicks: PlayersPicks
) = overUnder.contest?.takeIf {
    it.progress == Running || it.progress == Final
}?.run {
    // This algorithm matters only if there is contention between more than one top ranked player.
    firstOrNull()?.takeIf { it.players.size > 1 }?.let { topRanked ->
        topRanked.players.forEach { player ->
            overUnder.update(player, requireNotNull(playersPicks[player]))
        }

        val overUnderEliminations = topRanked.players - overUnder.nearestPlayers()
        val newTopRanked = overUnder.nearestPlayers()
        val firstPlaceRankings = Ranking(score = topRanked.score, players = newTopRanked)
        val secondPlaceRankings = Ranking(score = topRanked.score, players = overUnderEliminations)

        if (
            firstPlaceRankings.players.isEmpty() ||
            secondPlaceRankings.players.isEmpty()
        ) return this@include

        val remainingRankings = drop(1)

        listOf(firstPlaceRankings, secondPlaceRankings) + remainingRankings
    }
} ?: this

private fun Scores.awardPointTo(player: Player) {
    put(player, getOrDefault(player, 0) + 1)
}

/**
 * Calculates the most favorable outcome for each player.
 *
 * @returns the set of players that are still in game contention.
 */
private fun List<Contest>.potentialOutcomes(
    scores: Scores,
    playersPicks: PlayersPicks
): Set<Player> {
    val players = playersPicks.keys
    val playerOutcomes = players.associateWith { scores.toMutableMap() as Scores }

    forEach { contest ->
        players.forEach { player ->
            playersPicks[player]?.selection(contest)?.let { franchise ->
                val potentialScores = playerOutcomes[player]!!

                awardPoints(
                    franchise,
                    potentialScores,
                    playersPicks
                )
            } ?: run {
                // TODO Award points to other players if they selected any team? May not matter.
            }
        }
    }

    return playerOutcomes.mapNotNull {
        val player = it.key
        val ranks = buildRankings(it.value)

        if (ranks.firstOrNull()?.players?.contains(player) == true) player
        else null
    }.toSet()
}

private fun awardPoints(
    victor: Franchise,
    scores: Scores,
    playersPicks: PlayersPicks
) {
    playersPicks.forEach { (player, picks) ->
        if (picks.selected(victor)) scores.awardPointTo(player)
    }
}
