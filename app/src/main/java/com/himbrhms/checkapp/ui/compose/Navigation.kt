package com.himbrhms.checkapp.ui.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.himbrhms.checkapp.common.Routes

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
        composable(Routes.TODO_LIST) {
            CheckListScreen(
                onNavigate = { navigateEvent ->
                    navController.navigate(navigateEvent.route)
                }
            )
        }
        composable(
            route = Routes.ADD_EDIT_TODO + "?itemId={itemId}",
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