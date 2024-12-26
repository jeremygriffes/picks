package net.slingspot.picks.data

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Interface for CRUD operations.
 *
 * @param P primary key type
 * @param T data type
 */
interface Crud<P : Any, T : Any> {
    suspend fun get(key: P): T?

    suspend fun insert(type: T)

    suspend fun update(type: T)

    suspend fun delete(key: P)
}

suspend fun <P : Any, T : Any> Crud<P, T>.upsert(type: T) {
    try {
        insert(type)
    } catch (_: Throwable) {
        update(type)
    }
}

abstract class InMemoryTable<P : Any, T : Any> : Crud<P, T> {
    private val mutex = Mutex()
    private val data = mutableMapOf<P, T>()

    abstract fun keyOf(type: T): P

    override suspend fun get(key: P) = mutex.withLock { data[key] }

    override suspend fun insert(type: T) {
        mutex.withLock {
            val key = keyOf(type)
            check(!data.containsKey(key))
            data[key] = type
        }
    }

    override suspend fun update(type: T) {
        mutex.withLock {
            val key = keyOf(type)
            check(data.containsKey(key))
            data[key] = type
        }
    }

    override suspend fun delete(key: P) {
        mutex.withLock {
            data.remove(key)
        }
    }

    suspend fun clear() {
        mutex.withLock {
            data.clear()
        }
    }
}
