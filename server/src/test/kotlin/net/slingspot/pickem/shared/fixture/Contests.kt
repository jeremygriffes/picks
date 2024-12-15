package net.slingspot.pickem.shared.fixture

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import net.slingspot.pickem.shared.fixture.Franchises.atl
import net.slingspot.pickem.shared.fixture.Franchises.az
import net.slingspot.pickem.shared.fixture.Franchises.bal
import net.slingspot.pickem.shared.fixture.Franchises.buf
import net.slingspot.pickem.shared.fixture.Franchises.car
import net.slingspot.pickem.shared.fixture.Franchises.chi
import net.slingspot.pickem.shared.fixture.Franchises.cin
import net.slingspot.pickem.shared.fixture.Franchises.cle
import net.slingspot.pickem.shared.fixture.Franchises.dal
import net.slingspot.pickem.shared.fixture.Franchises.den
import net.slingspot.pickem.shared.fixture.Franchises.det
import net.slingspot.pickem.shared.fixture.Franchises.gb
import net.slingspot.pickem.shared.fixture.Franchises.hou
import net.slingspot.pickem.shared.fixture.Franchises.ind
import net.slingspot.pickem.shared.fixture.Franchises.jax
import net.slingspot.pickem.shared.fixture.Franchises.kc
import net.slingspot.pickem.shared.fixture.Franchises.lac
import net.slingspot.pickem.shared.fixture.Franchises.lar
import net.slingspot.pickem.shared.fixture.Franchises.lv
import net.slingspot.pickem.shared.fixture.Franchises.mia
import net.slingspot.pickem.shared.fixture.Franchises.min
import net.slingspot.pickem.shared.fixture.Franchises.ne
import net.slingspot.pickem.shared.fixture.Franchises.no
import net.slingspot.pickem.shared.fixture.Franchises.nyg
import net.slingspot.pickem.shared.fixture.Franchises.nyj
import net.slingspot.pickem.shared.fixture.Franchises.phi
import net.slingspot.pickem.shared.fixture.Franchises.pit
import net.slingspot.pickem.shared.fixture.Franchises.sea
import net.slingspot.pickem.shared.fixture.Franchises.sf
import net.slingspot.pickem.shared.fixture.Franchises.tb
import net.slingspot.pickem.shared.fixture.Franchises.ten
import net.slingspot.pickem.shared.fixture.Franchises.wsh
import net.slingspot.pickem.shared.model.Progress
import net.slingspot.pickem.shared.model.football.Contest
import net.slingspot.pickem.shared.model.football.Franchise
import net.slingspot.pickem.shared.model.football.Score

val week1game01 = bal at kc on "09-05T17:15"
val week1game02 = gb at phi on "09-06T06:30"
val week1game03 = car at no on "09-08T10:00"
val week1game04 = min at nyg on "09-08T10:00"
val week1game05 = ne at cin on "09-08T10:00"
val week1game06 = pit at atl on "09-08T10:00"
val week1game07 = hou at ind on "09-08T10:00"
val week1game08 = az at buf on "09-08T10:00"
val week1game09 = ten at chi on "09-08T10:00"
val week1game10 = jax at mia on "09-08T10:00"
val week1game11 = den at sea on "09-08T10:00"
val week1game12 = lv at lac on "09-08T13:15"
val week1game13 = dal at cle on "09-08T13:25"
val week1game14 = wsh at tb on "09-08T13:25"
val week1game15 = lar at det on "09-08T17:30"
val week1game16 = nyj at sf on "09-09T17:15"

val week12game01 = pit at cle on "11-21T17:15"
val week12game02 = ten at hou on "11-24T10:00"
val week12game03 = min at chi on "11-24T10:00"
val week12game04 = dal at wsh on "11-24T10:00"
val week12game05 = ne at mia on "11-24T10:00"
val week12game06 = kc at car on "11-24T10:00"
val week12game07 = tb at nyg on "11-24T10:00"
val week12game08 = det at ind on "11-24T10:00"
val week12game09 = den at lv on "11-24T13:05"
val week12game10 = az at sea on "11-24T13:05"
val week12game11 = az at sea on "11-24T13:25"
val week12game12 = sf at gb on "11-24T13:25"
val week12game13 = phi at lar on "11-24T17:20"
val week12game14 = bal at lac on "11-25T17:15"

val week1 = setOf(
    week1game01.final(kc, 27 to 20),
    week1game02.final(phi, 34 to 29),
    week1game03.final(no, 47 to 10),
    week1game04.final(min, 28 to 6),
    week1game05.final(ne, 16 to 10),
    week1game06.final(pit, 18 to 10),
    week1game07.final(hou, 29 to 27),
    week1game08.final(buf, 34 to 28),
    week1game09.final(chi, 24 to 17),
    week1game10.final(mia, 20 to 17),
    week1game11.final(sea, 26 to 20),
    week1game12.final(lac, 22 to 10),
    week1game13.final(dal, 33 to 17),
    week1game14.final(tb, 37 to 20),
    week1game15.final(det, 26 to 20),
    week1game16.final(sf, 32 to 19)
)

val week12 = setOf(
    week12game01,
    week12game02,
    week12game03,
    week12game04,
    week12game05,
    week12game06,
    week12game07,
    week12game08,
    week12game09,
    week12game10,
    week12game11,
    week12game12,
    week12game13,
    week12game14
)

data class Versus(val away: Franchise, val home: Franchise)

infix fun Franchise.at(home: Franchise) = Versus(this, home)

infix fun Versus.on(start: String): Contest {
    return Contest(
        home = home,
        away = away,
        scheduledTime = LocalDateTime.parse("2024-$start").toInstant(UtcOffset(hours = -8))
    )
}

private fun Contest.build(
    prog: Progress,
    team: Franchise,
    scores: Pair<Int, Int>
) = this.also {
    require(home == team || away == team)

    score = Score(
        home = if (team == home) scores.first else scores.second,
        away = if (team == away) scores.first else scores.second
    )
    progress = prog
}

fun Contest.running(
    team: Franchise,
    scores: Pair<Int, Int>
) = build(
    Progress.Running,
    team,
    scores
)

fun Contest.final(
    team: Franchise,
    scores: Pair<Int, Int>
) = build(
    Progress.Final,
    team,
    scores
)
