package com.himbrhms.checkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.himbrhms.checkapp.ui.Navigation
import com.himbrhms.checkapp.util.Logger
import com.himbrhms.checkapp.util.className

class MainActivity : ComponentActivity() {

    companion object {
        private val logger = Logger(this::class.className)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.debug("onCreate")
        setContent {
            Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                Navigation()
            }
        }
    }
}
