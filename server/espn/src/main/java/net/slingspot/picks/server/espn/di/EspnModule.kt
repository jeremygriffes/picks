package net.slingspot.picks.server.espn.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import net.slingspot.picks.server.espn.data.EspnApi
import net.slingspot.picks.server.espn.data.cache.EspnCache
import net.slingspot.picks.server.espn.domain.EspnRepository
import net.slingspot.server.nfl.NflDataSource
import okio.FileSystem
import org.koin.dsl.module

val espnModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        prettyPrint = true
                    }
                )
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.NONE
            }
        }
    }
    single { EspnApi(get()) }
    single { EspnCache(FileSystem.SYSTEM) }
    single<NflDataSource> { EspnRepository(get(), get(), get(), get()) }
}