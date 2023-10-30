package com.seo4d696b75.android.switchbot_lock_ext.ui.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.Screen

val NavBackStackEntry.currentBottomTab: Screen.BottomTab?
    get() {
        val route = destination.route ?: return null
        return when (route.split("/").firstOrNull()) {
            Screen.Home.tabRoute -> Screen.Home
            Screen.Device.tabRoute -> Screen.Device
            Screen.User.tabRoute -> Screen.User
            else -> null
        }
    }

fun NavController.navigateSingleTop(route: String) {
    navigate(route) {
        launchSingleTop = true
    }
}
