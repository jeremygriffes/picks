package net.slingspot.pickem.shared.fixture

import net.slingspot.pickem.shared.fixture.Franchises.atl
import net.slingspot.pickem.shared.fixture.Franchises.az
import net.slingspot.pickem.shared.fixture.Franchises.buf
import net.slingspot.pickem.shared.fixture.Franchises.car
import net.slingspot.pickem.shared.fixture.Franchises.chi
import net.slingspot.pickem.shared.fixture.Franchises.cin
import net.slingspot.pickem.shared.fixture.Franchises.cle
import net.slingspot.pickem.shared.fixture.Franchises.dal
import net.slingspot.pickem.shared.fixture.Franchises.det
import net.slingspot.pickem.shared.fixture.Franchises.gb
import net.slingspot.pickem.shared.fixture.Franchises.jax
import net.slingspot.pickem.shared.fixture.Franchises.kc
import net.slingspot.pickem.shared.fixture.Franchises.lac
import net.slingspot.pickem.shared.fixture.Franchises.lar
import net.slingspot.pickem.shared.fixture.Franchises.lv
import net.slingspot.pickem.shared.fixture.Franchises.mia
import net.slingspot.pickem.shared.fixture.Franchises.min
import net.slingspot.pickem.shared.fixture.Franchises.no
import net.slingspot.pickem.shared.fixture.Franchises.nyg
import net.slingspot.pickem.shared.fixture.Franchises.nyj
import net.slingspot.pickem.shared.fixture.Franchises.phi
import net.slingspot.pickem.shared.fixture.Franchises.pit
import net.slingspot.pickem.shared.fixture.Franchises.sea
import net.slingspot.pickem.shared.fixture.Franchises.sf
import net.slingspot.pickem.shared.fixture.Franchises.tb
import net.slingspot.pickem.shared.fixture.Franchises.ten
import net.slingspot.pickem.shared.model.entities.Picks
import net.slingspot.pickem.shared.model.entities.Player

val lauren = Player(name = "lauren")
val jeremy = Player(name = "jeremy")
val heather = Player(name = "heather")
val purdy = Player(name = "purdy")
val erica = Player(name = "erica")
val turtle = Player(name = "turtle")
val kati = Player(name = "kati")
val jason = Player(name = "jason")
val shane = Player(name = "shane")

val packersLions = gb at det on "12-05T17:00"
val brownsSteelers = cle at pit on "12-08T10:00"
val saintsGiants = no at nyg on "12-08T10:00"
val jetsDolphins = nyj at mia on "12-08T10:00"
val jaguarsTitans = jax at ten on "12-08T10:00"
val falconsVikings = atl at min on "12-08T10:00"
val panthersEagles = car at phi on "12-08T10:00"
val raidersBuccs = lv at tb on "12-08T10:00"
val seahawksCardinals = sea at az on "12-08T13:00"
val bears49ers = chi at sf on "12-08T13:00"
val billsRams = buf at lar on "12-08T13:00"
val chargersChiefs = lac at kc on "12-08T17:00"
val bengalsCowboys = cin at dal on "12-09T17:00"

val week14playersPicks = mapOf(
    lauren to Picks(
        setOf(
            det,
            pit,
            nyg,
            mia,
            ten,
            min,
            phi,
            tb,
            sea,
            chi,
            buf,
            kc,
            cin
        ),
        40
    ),
    jeremy to Picks(
        setOf(
            det,
            pit,
            no,
            mia,
            ten,
            min,
            phi,
            tb,
            az,
            sf,
            buf,
            kc,
            cin
        ),
        47
    ),
    heather to Picks(
        setOf(
            det,
            pit,
            nyg,
            mia,
            ten,
            min,
            phi,
            tb,
            az,
            sf,
            buf,
            kc,
            cin
        ),
        38
    ),
    purdy to Picks(
        setOf(
            det,
            pit,
            no,
            mia,
            ten,
            min,
            phi,
            tb,
            az,
            sf,
            buf,
            kc,
            cin
        ),
        50
    ),
    erica to Picks(
        setOf(
            gb,
            pit,
            no,
            mia,
            jax,
            min,
            phi,
            lv,
            sea,
            sf,
            buf,
            kc,
            dal
        ),
        48
    ),
    turtle to Picks(
        setOf(
            det,
            pit,
            no,
            mia,
            ten,
            min,
            phi,
            tb,
            sea,
            chi,
            buf,
            lac,
            cin
        ),
        44
    ),
    kati to Picks(
        setOf(
            gb,
            pit,
            no,
            mia,
            jax,
            min,
            phi,
            tb,
            az,
            chi,
            buf,
            kc,
            cin
        ),
        45
    ),
    jason to Picks(
        setOf(
            gb,
            pit,
            no,
            mia,
            ten,
            min,
            phi,
            tb,
            az,
            sf,
            buf,
            lac,
            cin
        ),
        51
    ),
    shane to Picks(
        setOf(
            det,
            pit,
            no,
            mia,
            ten,
            min,
            phi,
            tb,
            sea,
            sf,
            buf,
            kc,
            cin
        ),
        53
    )
)

val week14contests = setOf(
    packersLions.final(det, 34 to 31),
    brownsSteelers.final(pit, 27 to 14),
    saintsGiants.final(no, 14 to 11),
    jetsDolphins.final(mia, 32 to 26),
    jaguarsTitans.final(jax, 10 to 6),
    falconsVikings.final(min, 42 to 21),
    panthersEagles.final(phi, 22 to 16),
    raidersBuccs.final(tb, 28 to 13),
    seahawksCardinals.final(sea, 30 to 18),
    bears49ers.final(sf, 38 to 13),
    billsRams,
    chargersChiefs,
    bengalsCowboys
)
