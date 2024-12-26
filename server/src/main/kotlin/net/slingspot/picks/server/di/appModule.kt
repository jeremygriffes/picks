package net.slingspot.picks.server.di

import kotlinx.datetime.Clock
import org.koin.dsl.module

val appModule = module {
    single<Clock> { Clock.System }
}
