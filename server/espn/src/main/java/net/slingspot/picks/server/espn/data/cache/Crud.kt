package net.slingspot.picks.server.espn.data.cache

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
