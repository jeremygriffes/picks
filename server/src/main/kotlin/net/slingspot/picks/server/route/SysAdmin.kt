package net.slingspot.picks.server.route

import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import net.slingspot.picks.server.data.PicksRepository

fun Routing.sysAdmin(picksRepository: PicksRepository) {
    get("/users") {
        val users = picksRepository.users()
        call.respond(users)
    }
}
