package net.slingspot.picks.fixture

import net.slingspot.picks.model.entities.Picks
import net.slingspot.picks.model.entities.Player

val player1 = Player(
    id = "1",
    name = "Alice"
)

val player2 = Player(
    id = "2",
    name = "Bob"
)

val player3 = Player(
    id = "3",
    name = "Charlie"
)

val players = setOf(player1, player2, player3)

val player1week1picks = Picks(
    selectedVictors = setOf(
        week1game01.home,
        week1game02.home,
        week1game03.home,
        week1game04.home,
        week1game05.home,
        week1game06.home,
        week1game07.home,
        week1game08.home,
        week1game09.home,
        week1game10.home,
        week1game11.home,
        week1game12.home,
        week1game13.home,
        week1game14.home,
        week1game15.home,
        week1game16.home
    ),
    overUnder = 45
)

val player2week1picks = Picks(
    selectedVictors = setOf(
        week1game01.home,
        week1game02.home,
        week1game03.home,
        week1game04.home,
        week1game05.home,
        week1game06.home,
        week1game07.home,
        week1game08.home,
        week1game09.home,
        week1game10.home,
        week1game11.home,
        week1game12.home,
        week1game13.home,
        week1game14.home,
        week1game15.home,
        week1game16.away
    ),
    overUnder = 44
)

val player3week1picks = Picks(
    selectedVictors = setOf(
        week1game01.away,
        week1game02.away,
        week1game03.away,
        week1game04.away,
        week1game05.away,
        week1game06.away,
        week1game07.away,
        week1game08.away,
        week1game09.away,
        week1game10.away,
        week1game11.away,
        week1game12.away,
        week1game13.away,
        week1game14.away,
        week1game15.away,
        week1game16.away
    ),
    overUnder = 46
)

val player1week12picks = Picks(
    selectedVictors = setOf(
        week12game01.home,
        week12game02.home,
        week12game03.home,
        week12game04.home,
        week12game05.home,
        week12game06.home,
        week12game07.home,
        week12game08.home,
        week12game09.home,
        week12game10.home,
        week12game11.home,
        week12game12.home,
        week12game13.home,
        week12game14.home
    ),
    overUnder = 45
)

val player2week12picks = Picks(
    selectedVictors = setOf(
        week12game01.home,
        week12game02.home,
        week12game03.home,
        week12game04.home,
        week12game05.home,
        week12game06.home,
        week12game07.home,
        week12game08.home,
        week12game09.home,
        week12game10.home,
        week12game11.home,
        week12game12.home,
        week12game13.home,
        week12game14.home
    ),
    overUnder = 44
)

val player3week12picks = Picks(
    selectedVictors = setOf(
        week12game01.away,
        week12game02.away,
        week12game03.away,
        week12game04.away,
        week12game05.away,
        week12game06.away,
        week12game07.away,
        week12game08.away,
        week12game09.away,
        week12game10.away,
        week12game11.away,
        week12game12.away,
        week12game13.away,
        week12game14.away
    ),
    overUnder = 46
)

val week1picks = mapOf(
    player1 to player1week1picks,
    player2 to player2week1picks,
    player3 to player3week1picks
)

val week12picks = mapOf(
    player1 to player1week12picks,
    player2 to player2week12picks,
    player3 to player3week12picks
)
