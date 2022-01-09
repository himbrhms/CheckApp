package com.himbrhms.checkapp.util

open class Cache<KEY, VALUE> {
    private val values = mutableMapOf<KEY, VALUE>()

    @Synchronized
    fun insert(key: KEY, value: VALUE) {
        values[key] = value
    }

    @Synchronized
    fun delete(key: KEY) = values.remove(key)

    @Synchronized
    fun getValue(key: KEY): VALUE? = values[key]

    @Synchronized
    fun getAllValues(): Map<KEY, VALUE> = values

    @Synchronized
    fun clear() = values.clear()
}