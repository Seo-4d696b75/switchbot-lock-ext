package com.seo4d696b75.android.switchbot_lock_ext.ui.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.Screen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.HomeScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.dialog.StatusDetailDialog

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
            HomeScreen(
                navigateToStatusDetail = {
                    val route = Screen.Home.StatusDetailDialog.createRoute(it)
                    navController.navigateSingleTop(route)
                },
            )
        }

        dialog(
            route = Screen.Home.StatusDetailDialog.route,
            arguments = listOf(
                navArgument("deviceId") {
                    type = NavType.StringType
                    nullable = false
                },
            ),
        ) { backStackEntry ->
            val topBackStackEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Screen.Home.Top.route)
            }
            val deviceId = requireNotNull(
                backStackEntry.arguments?.getString("deviceId")
            )
            StatusDetailDialog(
                viewModel = hiltViewModel(topBackStackEntry),
                deviceId = deviceId,
                onDismiss = { navController.popBackStack() },
            )
        }
    }
}
