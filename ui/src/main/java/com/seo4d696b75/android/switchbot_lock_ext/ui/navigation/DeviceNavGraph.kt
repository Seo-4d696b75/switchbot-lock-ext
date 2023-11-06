package com.seo4d696b75.android.switchbot_lock_ext.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.Screen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceList.DeviceListScreen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.deviceRegistration.DeviceRegistrationScreen

fun NavGraphBuilder.deviceNavGraph(
    navController: NavController,
) {
    navigation(
        startDestination = Screen.Device.List.route,
        route = Screen.Device.tabRoute,
    ) {
        composable(
            route = Screen.Device.List.route,
        ) {
            DeviceListScreen(
                navigateToRegistration = {
                    navController.navigateSingleTop(Screen.Device.Registration.route)
                },
            )
        }
        composable(
            route = Screen.Device.Registration.route,
        ) {
            DeviceRegistrationScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}

