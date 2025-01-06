package net.slingspot.picks.server.data.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.koin.dsl.module

val dataModule = module {
    single { CoroutineScope(Dispatchers.IO) }
    single<Clock> { Clock.System }
}
