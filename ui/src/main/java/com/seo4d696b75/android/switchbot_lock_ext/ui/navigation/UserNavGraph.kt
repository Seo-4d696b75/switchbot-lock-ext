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
    navigation<Screen.User.Tab>(
        startDestination = Screen.User.Top,
    ) {
        composable<Screen.User.Top> {
            UserScreen(
                navigateToEdit = {
                    navController.navigateSingleTop(Screen.User.Edit)
                },
            )
        }

        composable<Screen.User.Edit> {
            EditUserScreen(
                navigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
