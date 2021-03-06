package com.himbrhms.checkapp.util

open class Cache<KEY, VALUE> {
    private val values = mutableMapOf<KEY?, VALUE>()

    @Synchronized
    fun insert(key: KEY, value: VALUE) {
        values[key] = value
    }

    @Synchronized
    fun remove(key: KEY) = values.remove(key)

    @Synchronized
    fun isEmpty(): Boolean = values.isEmpty()

    @Synchronized
    fun contains(key: KEY): Boolean = values.containsKey(key)

    @Synchronized
    fun getValue(key: KEY): VALUE? = values[key]

    @Synchronized
    fun getCacheItems(): Map<KEY?, VALUE> = values

    @Synchronized
    fun getAllValues(): List<VALUE> = values.values.toList()

    @Synchronized
    fun clear() = values.clear()
}
