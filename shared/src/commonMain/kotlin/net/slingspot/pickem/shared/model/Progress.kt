package net.slingspot.pickem.shared.model

sealed class Progress {
    data object Pending : Progress()
    data object Running : Progress()
    data object Final : Progress()
    data object Cancelled : Progress()
}
