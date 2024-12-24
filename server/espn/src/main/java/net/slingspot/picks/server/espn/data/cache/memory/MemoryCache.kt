package net.slingspot.picks.server.espn.data.cache.memory

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.slingspot.picks.server.espn.data.cache.Cache
import net.slingspot.picks.server.espn.data.cache.CompetitionTable
import net.slingspot.picks.server.espn.data.cache.EventTable
import net.slingspot.picks.server.espn.data.cache.SeasonTable
import net.slingspot.picks.server.espn.data.cache.TeamTable
import net.slingspot.picks.server.espn.data.cache.WeekTable
import net.slingspot.picks.server.espn.model.Competition
import net.slingspot.picks.server.espn.model.Event
import net.slingspot.picks.server.espn.model.Season
import net.slingspot.picks.server.espn.model.Team
import net.slingspot.picks.server.espn.model.Week

class MemoryCache : Cache {
    val mutex = Mutex()

    override val seasonTable = object : SeasonTable {
        private val data = mutableMapOf<Int, Season>()

        override suspend fun get(key: Int) = mutex.withLock {
            data[key]
        }

        override suspend fun insert(type: Season) = mutex.withLock {
            check(!data.containsKey(type.year))

            data[type.year] = type
        }

        override suspend fun update(type: Season) = mutex.withLock {
            data[type.year] = type
        }

        override suspend fun delete(key: Int) {
            mutex.withLock {
                data.remove(key)
            }
        }
    }

    override val teamTable = object : TeamTable {
        private val data = mutableMapOf<String, Team>()

        override suspend fun get(key: String) = mutex.withLock {
            data[key]
        }

        override suspend fun insert(type: Team) = mutex.withLock {
            check(!data.containsKey(type.guid))

            data[type.guid] = type
        }

        override suspend fun update(type: Team) = mutex.withLock {
            data[type.guid] = type
        }

        override suspend fun delete(key: String) {
            mutex.withLock {
                data.remove(key)
            }
        }
    }

    override val weekTable = object : WeekTable {
        private val data = mutableMapOf<String, Week>()

        override suspend fun get(key: String) = mutex.withLock {
            data[key]
        }

        override suspend fun insert(type: Week) = mutex.withLock {
            check(!data.containsKey(type.startDate))

            data[type.startDate] = type
        }

        override suspend fun update(type: Week) = mutex.withLock {
            data[type.startDate] = type
        }

        override suspend fun delete(key: String) {
            mutex.withLock {
                data.remove(key)
            }
        }
    }

    override val eventTable = object : EventTable {
        private val data = mutableMapOf<String, Event>()

        override suspend fun get(key: String) = mutex.withLock {
            data[key]
        }

        override suspend fun insert(type: Event) = mutex.withLock {
            check(!data.containsKey(type.id))

            data[type.id] = type
        }

        override suspend fun update(type: Event) = mutex.withLock {
            data[type.id] = type
        }

        override suspend fun delete(key: String) {
            mutex.withLock {
                data.remove(key)
            }
        }
    }

    override val competitionTable = object : CompetitionTable {
        private val data = mutableMapOf<String, Competition>()

        override suspend fun get(key: String) = mutex.withLock {
            data[key]
        }

        override suspend fun insert(type: Competition) = mutex.withLock {
            check(!data.containsKey(type.id))

            data[type.id] = type
        }

        override suspend fun update(type: Competition) = mutex.withLock {
            data[type.id] = type
        }

        override suspend fun delete(key: String) {
            mutex.withLock {
                data.remove(key)
            }
        }
    }
}