package net.slingspot.picks.fixture

import net.slingspot.picks.model.entities.Game
import net.slingspot.picks.model.entities.GameStatus
import net.slingspot.picks.model.entities.Player
import net.slingspot.picks.model.entities.Ranking
import kotlin.test.assertEquals

fun assertGamesEquals(expected: Game, actual: Game) {
    assertEquals(expected.id, actual.id, "Game.id not equal")
    assertEquals(
        expected.overUnderContest,
        actual.overUnderContest,
        "Game.overUnderContest not equal"
    )
    assert(expected.contests.containsAll(actual.contests)) { "Game.contests not equal" }
    assert(actual.contests.containsAll(expected.contests)) { "Game.contests not equal" }

    assertEquals(
        expected.playersPicks.keys.sortedBy { it.name },
        actual.playersPicks.keys.sortedBy { it.name },
        "Game.playersPicks not equal"
    )

    expected.playersPicks.forEach { entry ->
        val player = entry.key
        val expectedPicks = entry.value.selectedVictors.sortedBy { it.name }
        val actualPicks = actual.playersPicks[player]?.selectedVictors?.sortedBy { it.name }
        assertEquals(expectedPicks, actualPicks, "Game.playersPicks not equal")
        assertEquals(
            entry.value.overUnder,
            actual.playersPicks[player]?.overUnder,
            "Game.playersPicks.overUnder not equal"
        )
    }
}

fun assertGameStatusEquals(expected: GameStatus, actual: GameStatus) {
    assertEquals(expected.progress, actual.progress, "GameStatus.progress not equal")
    assertScoresEquals(expected.scores, actual.scores)
    assertRankingsEquals(expected.rankings, actual.rankings)
    assertContendersEquals(expected.contenders, actual.contenders)
}

fun assertScoresEquals(expected: Map<Player, Int>, actual: Map<Player, Int>) {
    assertEquals(
        expected.keys.sortedBy { it.name },
        actual.keys.sortedBy { it.name },
        "GameStatus.scores not equal"
    )

    expected.forEach { entry ->
        val player = entry.key
        val expectedScore = entry.value
        val actualScore = actual[player]
        assertEquals(expectedScore, actualScore, "GameStatus.scores not equal")
    }
}

fun assertRankingsEquals(expected: List<Ranking>, actual: List<Ranking>) {
    expected.forEachIndexed { index, expectedRanking ->
        val actualRanking = actual[index]
        assertEquals(
            expectedRanking.score,
            actualRanking.score,
            "GameStatus.rankings.score not equal"
        )

        val expectedPlayers = expectedRanking.players.sortedBy { it.name }
        val actualPlayers = actualRanking.players.sortedBy { it.name }

        assertEquals(expectedPlayers, actualPlayers, "GameStatus.rankings.players not equal")
    }
}

fun assertContendersEquals(expected: Set<Player>, actual: Set<Player>) {
    assertEquals(
        expected.sortedBy { it.name },
        actual.sortedBy { it.name },
        "GameStatus.contenders not equal"
    )
}