package net.slingspot.picks.server.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.koin.dsl.module

val appModule = module {
    single<Clock> { Clock.System }
    single { CoroutineScope(Dispatchers.IO) }
}
