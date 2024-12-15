package net.slingspot.pickem.shared.fixture

import net.slingspot.pickem.server.game.calculate
import net.slingspot.pickem.shared.model.Progress.Final
import net.slingspot.pickem.shared.model.Progress.Running
import net.slingspot.pickem.shared.model.entities.Game
import net.slingspot.pickem.shared.model.entities.GameStatus
import net.slingspot.pickem.shared.model.entities.Picks
import net.slingspot.pickem.shared.model.entities.Ranking
import net.slingspot.pickem.shared.model.football.Score
import kotlin.test.Test

class GameCalculatorTest {
    @Test
    fun `game with no players and no contests returns no victors`() {
        val game = Game()
        val expected = GameStatus()
        val actual = game.calculate()

        assertGameStatusEquals(expected, actual)
    }

    @Test
    fun `no contests results in 0 score`() {
        val playersPicks = players.associateWith { Picks() }
        val game = Game(playersPicks = playersPicks)

        val expected = GameStatus(
            scores = players.associateWith { 0 },
            rankings = listOf(Ranking(0, players)),
            contenders = players
        )

        val actual = game.calculate()

        assertGameStatusEquals(expected, actual)
    }

    @Test
    fun `multiple players are grouped to a 0 score`() {
        val playersPicks = players.associateWith { Picks() }
        val game = Game(playersPicks = playersPicks)

        val expected = GameStatus(
            scores = players.associateWith { 0 },
            rankings = listOf(Ranking(0, players)),
            contenders = players
        )

        val actual = game.calculate()

        assertGameStatusEquals(expected, actual)
    }

    @Test
    fun `pending contests have no effect on scores`() {
        val game = Game(
            playersPicks = week12picks,
            contests = week12
        )

        val expected = GameStatus(
            scores = players.associateWith { 0 },
            rankings = listOf(Ranking(0, players)),
            contenders = players
        )

        val actual = game.calculate()

        assertGameStatusEquals(expected, actual)
    }

    @Test
    fun `running contests have no effect on scores`() {
        // New England leads a contest in progress. Each player has selected NE to win.
        // The contest is not final, so no points are awarded to the players.
        val contest =
            (Franchises.ne at Franchises.bal on "11-17T17:00").running(Franchises.ne, 20 to 7)
        val playersPicks = players.associateWith { Picks(setOf(Franchises.ne)) }
        val contests = setOf(contest)
        val game = Game(
            playersPicks = playersPicks,
            contests = contests
        )

        val expected = GameStatus(
            scores = players.associateWith { 0 },
            rankings = listOf(Ranking(0, players)),
            contenders = players,
            progress = Running
        )

        val actual = game.calculate()

        assertGameStatusEquals(expected, actual)
    }

    @Test
    fun `final contests award points`() {
        // New England has won a contest. Each player has selected NE to win.
        val contest =
            (Franchises.ne at Franchises.bal on "11-17T17:00").final(Franchises.ne, 20 to 7)
        val playersPicks = players.associateWith { Picks(setOf(Franchises.ne)) }
        val contests = setOf(contest)
        val game = Game(
            playersPicks = playersPicks,
            contests = contests,
            overUnderContest = null
        )

        val expected = GameStatus(
            scores = players.associateWith { 1 },
            rankings = listOf(Ranking(1, players)),
            contenders = players,
            progress = Final
        )

        val actual = game.calculate()

        assertGameStatusEquals(expected, actual)
    }

    @Test
    fun `contests ending in a tie award points to either pick`() {
        // New England and Baltimore tied. Players selecting either team are awarded a point.
        val contest =
            (Franchises.ne at Franchises.bal on "11-17T17:00").final(Franchises.ne, 20 to 20)
        val playersPicks = mapOf(
            player1 to Picks(setOf(Franchises.ne)),
            player2 to Picks(setOf(Franchises.bal)),
            player3 to Picks(setOf(Franchises.ne))
        )
        val contests = setOf(contest)
        val game = Game(
            playersPicks = playersPicks,
            contests = contests,
            overUnderContest = null
        )

        val expected = GameStatus(
            scores = players.associateWith { 1 },
            rankings = listOf(Ranking(1, players)),
            contenders = players,
            progress = Final
        )

        val actual = game.calculate()

        assertGameStatusEquals(expected, actual)
    }

    @Test
    fun `a single player with the most correct picks win`() {
        // New England beats Baltimore tied. One player wins.
        val contest =
            (Franchises.ne at Franchises.bal on "11-17T17:00").final(Franchises.ne, 20 to 3)
        val playersPicks = mapOf(
            player1 to Picks(setOf(Franchises.ne)),
            player2 to Picks(setOf(Franchises.bal)),
            player3 to Picks(setOf(Franchises.bal))
        )
        val contests = setOf(contest)
        val game = Game(
            playersPicks = playersPicks,
            contests = contests,
            overUnderContest = null
        )

        val expected = GameStatus(
            scores = mapOf(
                player1 to 1,
                player2 to 0,
                player3 to 0
            ),
            rankings = listOf(Ranking(1, setOf(player1)), Ranking(0, setOf(player2, player3))),
            contenders = setOf(player1),
            progress = Final
        )

        val actual = game.calculate()

        assertGameStatusEquals(expected, actual)
    }

    @Test
    fun `players can be eliminated before all contests are final`() {
        val newEnglandAtBaltimore = Franchises.ne at Franchises.bal on "11-17T17:00"
        val jacksonvilleAtNewOrleans = Franchises.jax at Franchises.no on "11-17T17:00"

        val contests = setOf(
            newEnglandAtBaltimore.final(Franchises.ne, 45 to 37),
            jacksonvilleAtNewOrleans
        )

        val playersPicks = mapOf(
            player1 to Picks(setOf(Franchises.ne, Franchises.jax)),
            player2 to Picks(setOf(Franchises.ne, Franchises.jax)),
            player3 to Picks(setOf(Franchises.bal, Franchises.jax)),
        )

        val game = Game(
            contests = contests,
            playersPicks = playersPicks,
            overUnderContest = null
        )

        val expected = GameStatus(
            scores = mapOf(
                player1 to 1,
                player2 to 1,
                player3 to 0
            ),
            rankings = listOf(Ranking(1, setOf(player1, player2)), Ranking(0, setOf(player3))),
            contenders = setOf(player1, player2),
            progress = Running
        )

        val actual = game.calculate()

        assertGameStatusEquals(expected, actual)
    }

    @Test
    fun `contest progress reduces contenders`() {
        val game = Game(
            contests = week14contests,
            playersPicks = week14playersPicks,
            overUnderContest = bengalsCowboys
        )

        var expected = setOf(erica, turtle, shane)
        var actual = game.calculate().contenders
        assertContendersEquals(expected, actual)

        billsRams.score = Score(home = 42, away = 44)
        billsRams.progress = Final

        expected = setOf(erica, turtle, shane)
        actual = game.calculate().contenders
        assertContendersEquals(expected, actual)

        chargersChiefs.score = Score(home = 19, away = 17)
        chargersChiefs.progress = Final

        expected = setOf(erica, shane)
        actual = game.calculate().contenders
        assertContendersEquals(expected, actual)

        bengalsCowboys.score = Score(home = 20, away = 27)
        bengalsCowboys.progress = Final

        expected = setOf(shane)
        actual = game.calculate().contenders
        assertContendersEquals(expected, actual)
    }
}