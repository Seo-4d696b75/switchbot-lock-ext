package com.seo4d696b75.android.switchbot_lock_ext.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.Screen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user.EditUserScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user.UserScreen

fun NavGraphBuilder.userNavGraph(
    navController: NavController,
) {
    navigation(
        startDestination = Screen.User.Top.route,
        route = Screen.User.tabRoute,
    ) {
        composable(
            route = Screen.User.Top.route,
        ) {
            UserScreen(
                navigateToEdit = {
                    navController.navigateSingleTop(Screen.User.Edit.route)
                },
            )
        }

        composable(
            route = Screen.User.Edit.route,
        ) {
            EditUserScreen(
                navigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
