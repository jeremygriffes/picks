package net.slingspot.picks.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Progress {
    @Serializable
    data object Pending : Progress()

    @Serializable
    data object Running : Progress()

    @Serializable
    data object Final : Progress()

    @Serializable
    data object Cancelled : Progress()
}
