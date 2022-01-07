package com.himbrhms.checkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.himbrhms.checkapp.ui.Navigation
import com.himbrhms.checkapp.ui.theme.CheckAppTheme
import com.himbrhms.checkapp.util.Logger
import com.himbrhms.checkapp.util.className
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private val logger = Logger(this::class.className)
    }

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi // ModalBottomSheetLayout
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
