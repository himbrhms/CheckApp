package com.himbrhms.checkapp

import android.app.Application
import com.himbrhms.checkapp.util.Logger
import com.himbrhms.checkapp.util.className
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CheckApp : Application() {

    companion object {
        private val logger = Logger(this::class.className)
    }

    override fun onCreate() {
        super.onCreate()
        logger.debug("onCreate")
    }
}
