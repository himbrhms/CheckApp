package com.himbrhms.checkapp.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.himbrhms.checkapp.common.Routes.NOTE_LIST_SCREEN
import com.himbrhms.checkapp.common.Routes.EDIT_NOTE_SCREEN
import com.himbrhms.checkapp.common.Routes.SPLASH_SCREEN

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Navigation(isStartup: Boolean) {
    val startScreen = if (isStartup) SPLASH_SCREEN else NOTE_LIST_SCREEN
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = startScreen) {
        composable(SPLASH_SCREEN) {
            SplashScreen(navController = navController)
        }
        composable(NOTE_LIST_SCREEN) {
            NoteListScreen(
                onNavigate = { navigateEvent ->
                    navController.navigate(navigateEvent.route)
                }
            )
        }
        composable(
            route = "$EDIT_NOTE_SCREEN?itemId={itemId}",
            arguments = listOf(
                navArgument(name = "itemId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) {
            EditNoteScreen(onPopBackStack = {
                navController.popBackStack()
            })
        }
    }
}