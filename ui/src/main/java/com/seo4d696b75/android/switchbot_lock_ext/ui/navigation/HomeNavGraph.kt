package com.seo4d696b75.android.switchbot_lock_ext.ui.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.Screen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.HomeScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.dialog.StatusDetailDialog

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
) {
    navigation<Screen.Home.Tab>(
        startDestination = Screen.Home.Top,
    ) {
        composable<Screen.Home.Top> {
            HomeScreen(
                navigateToStatusDetail = {
                    val route = Screen.Home.StatusDetailDialog(it)
                    navController.navigateSingleTop(route)
                },
            )
        }

        dialog<Screen.Home.StatusDetailDialog> { backStackEntry ->
            val topBackStackEntry = remember(backStackEntry) {
                navController.getBackStackEntry<Screen.Home.Top>()
            }
            val deviceId = backStackEntry
                .toRoute<Screen.Home.StatusDetailDialog>()
                .deviceId
            StatusDetailDialog(
                viewModel = hiltViewModel(topBackStackEntry),
                deviceId = deviceId,
                onDismiss = { navController.popBackStack() },
            )
        }
    }
}
