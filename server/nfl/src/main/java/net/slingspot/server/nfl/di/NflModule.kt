package net.slingspot.server.nfl.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.koin.dsl.module

val nflModule = module {
    single { CoroutineScope(Dispatchers.IO) }
    single<Clock> { Clock.System }
}
