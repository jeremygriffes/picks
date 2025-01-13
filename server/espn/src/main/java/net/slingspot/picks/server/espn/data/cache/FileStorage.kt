package net.slingspot.picks.server.espn.data.cache

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.slingspot.picks.data.Crud
import net.slingspot.picks.server.espn.model.EspnModel
import okio.FileSystem
import okio.FileSystem.Companion.SYSTEM_TEMPORARY_DIRECTORY
import okio.Path

abstract class FileStorage<P : Any, T : EspnModel>(
    private val fileSystem: FileSystem,
    pathName: String
) : Crud<P, T> {
    private val path = SYSTEM_TEMPORARY_DIRECTORY / ROOT_DIR / pathName

    protected val mutex = Mutex()
    protected val data = mutableMapOf<P, T>()

    abstract fun keyOf(type: T): P
    abstract fun deserialize(json: String): T

    override suspend fun get(key: P): T? = mutex.withLock {
        data[key] ?: load(key)?.also { data[key] = it }
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

    suspend fun all(): List<T> = mutex.withLock {
        data.values.toList().takeIf { it.isNotEmpty() }
            ?: run {
                loadAll().onEach { data[keyOf(it)] = it }
            }
    }

    override suspend fun drop() {
        try {
            fileSystem.list(path).forEach {
                fileSystem.delete(it)
            }
        } catch (_: Throwable) {
            // Ignored
        }

        data.clear()
    }

    private fun load(key: P): T? {
        return load(path / key.toString())
    }

    private fun loadAll(): List<T> {
        return if (!fileSystem.exists(path))
            emptyList()
        else
            fileSystem.list(path).mapNotNull { load(it) }
    }

    private fun load(filePath: Path): T? {
        return if (!fileSystem.exists(path))
            null
        else try {
            deserialize(
                fileSystem.read(filePath) { readUtf8() }
            )
        } catch (_: Throwable) {
            null
        }
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

    companion object {
        private const val ROOT_DIR = "picks_espn_cache"
    }
}
