package com.himbrhms.checkapp.util

import com.himbrhms.checkapp.BuildConfig
import timber.log.Timber
import kotlin.reflect.KClass

val <T: Any> KClass<T>.className
    get() = if (isCompanion) this.java.declaringClass.toString() else this.qualifiedName.toString()

class Logger(className: String) {

    companion object {
        init {
            if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        }
    }

    private val channelName = className.removePrefix("class ").removePrefix("me.mnemllr.")
    private val timber = Timber.tag(channelName)

    fun debug(msg: String) = timber.d(msg)

    fun info(msg: String) = timber.i(msg)

    fun warn(msg: String) = timber.w(msg)

    fun error(msg: String) = timber.e(msg)

    fun fatal(msg: String, throwable: Throwable) = timber.e(throwable, msg)
}
