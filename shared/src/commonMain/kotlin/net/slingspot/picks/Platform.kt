package net.slingspot.picks

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform