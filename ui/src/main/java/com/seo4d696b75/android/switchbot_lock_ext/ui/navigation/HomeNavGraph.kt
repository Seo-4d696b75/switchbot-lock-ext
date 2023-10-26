package com.seo4d696b75.android.switchbot_lock_ext.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.Screen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
) {
    navigation(
        startDestination = Screen.Home.Top.route,
        route = Screen.Home.tabRoute,
    ) {
        composable(
            route = Screen.Home.Top.route,
        ) {
            HomeScreen()
        }
    }
}
