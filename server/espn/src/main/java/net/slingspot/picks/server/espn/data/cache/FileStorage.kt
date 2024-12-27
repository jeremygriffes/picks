package net.slingspot.picks.server.espn.data.cache

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.slingspot.picks.data.Crud
import net.slingspot.picks.server.espn.model.EspnModel
import okio.FileSystem
import okio.Path

abstract class FileStorage<P : Any, T : EspnModel>(
    private val fileSystem: FileSystem,
    private val path: Path
) : Crud<P, T> {
    private val mutex = Mutex()
    private val data = mutableMapOf<P, T>()

    abstract fun keyOf(type: T): P
    abstract fun deserialize(json: String): T

    override suspend fun get(key: P) = mutex.withLock {
        data[key] ?: load(key).also { data[key] = it }
    }

    override suspend fun insert(type: T) {
        mutex.withLock {
            val key = keyOf(type)
            check(!data.containsKey(key))
            data[key] = type

            save(type)
        }
    }

    override suspend fun update(type: T) {
        mutex.withLock {
            val key = keyOf(type)
            check(data.containsKey(key))
            data[key] = type

            save(type)
        }
    }

    override suspend fun delete(key: P) {
        mutex.withLock {
            data.remove(key)

            remove(key)
        }
    }

    suspend fun clear() {
        mutex.withLock {
            data.clear()
        }
    }

    private fun load(key: P): T {
        val filePath = path / key.toString()
        val json = fileSystem.read(filePath) { readUtf8() }
        return deserialize(json)
    }

    private fun save(type: T) {
        fileSystem.createDirectories(path)
        val filePath = path / keyOf(type).toString()
        fileSystem.write(filePath) { writeUtf8(type.serialize()) }
    }

    private fun remove(key: P) {
        val filePath = path / key.toString()
        fileSystem.delete(filePath)
    }
}
