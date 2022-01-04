package com.himbrhms.checkapp.ui.compose

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.himbrhms.checkapp.common.Routes.CHECKLIST_SCREEN
import com.himbrhms.checkapp.common.Routes.EDIT_ITEM_SCREEN
import com.himbrhms.checkapp.common.Routes.SPLASH_SCREEN

@Composable
fun Navigation(isStartup: Boolean) {
    val startScreen = if (isStartup) SPLASH_SCREEN else CHECKLIST_SCREEN
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = startScreen) {
        composable(SPLASH_SCREEN) {
            SplashScreen(navController = navController)
        }
        composable(CHECKLIST_SCREEN) {
            CheckListScreen(
                onNavigate = { navigateEvent ->
                    navController.navigate(navigateEvent.route)
                }
            )
        }
        composable(
            route = "$EDIT_ITEM_SCREEN?itemId={itemId}",
            arguments = listOf(
                navArgument(name = "itemId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) {
            EditCheckListItemScreen(onPopBackStack = {
                navController.popBackStack()
            })
        }
    }
}