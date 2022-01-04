package com.himbrhms.checkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.himbrhms.checkapp.ui.compose.Navigation
import com.himbrhms.checkapp.ui.compose.theme.CheckAppTheme
import com.himbrhms.checkapp.util.Logger
import com.himbrhms.checkapp.util.className
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private val logger = Logger(this::class.className)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.debug("onCreate: savedInstanceState=$savedInstanceState")
        val isStartup = (savedInstanceState == null)
        setContent {
            CheckAppTheme {
                Navigation(isStartup)
            }
        }
    }
}
