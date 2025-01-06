package net.slingspot.picks.server.firebase.di

import net.slingspot.picks.server.data.DataAccess
import net.slingspot.picks.server.firebase.FirebaseRepository
import org.koin.dsl.module

val firebaseModule = module {
    single<DataAccess> { FirebaseRepository() }
}