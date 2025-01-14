package net.slingspot.picks.server.route

import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import net.slingspot.picks.Greeting

fun Routing.home() {
    get("/") {
        call.respondText("Ktor: ${Greeting().greet()}")
    }
}
