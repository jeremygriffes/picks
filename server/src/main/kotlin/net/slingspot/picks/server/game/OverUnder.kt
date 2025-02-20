package net.slingspot.picks.server.game

import net.slingspot.picks.model.Progress
import net.slingspot.picks.model.entities.Picks
import net.slingspot.picks.model.entities.Player
import net.slingspot.picks.model.football.Contest
import kotlin.math.abs

class OverUnder(val contest: Contest?) {
    private val closest = mutableListOf<Player>()
    private var nearestOverUnder = Int.MAX_VALUE

    fun update(player: Player, picks: Picks) {
        contest?.apply {
            if (progress != Progress.Final) return

            val overUnderProximity = abs(picks.overUnder - total)

            if (overUnderProximity < nearestOverUnder) {
                // This player, so far, is the closest to the combined score.
                nearestOverUnder = overUnderProximity
                closest.clear()
                closest.add(player)
            } else if (overUnderProximity == nearestOverUnder) {
                // This player and another player are tied for the closest to the combined score.
                closest.add(player)
            }
        }
    }

    fun nearestPlayers() = if (Progress.Final == contest?.progress) closest.toSet() else emptySet()
}