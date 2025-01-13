package net.slingspot.picks.data

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

    suspend fun drop()
}

suspend fun <P : Any, T : Any> Crud<P, T>.upsert(type: T) {
    try {
        insert(type)
    } catch (_: Throwable) {
        update(type)
    }
}
