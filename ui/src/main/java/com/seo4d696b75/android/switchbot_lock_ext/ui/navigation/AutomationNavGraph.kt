package com.seo4d696b75.android.switchbot_lock_ext.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.Screen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.automationList.AutomationListScreen

fun NavGraphBuilder.automationNavGraph(
    navController: NavController,
) {
    navigation(
        startDestination = Screen.Automation.List.route,
        route = Screen.Automation.tabRoute,
    ) {
        composable(
            route = Screen.Automation.List.route,
        ) {
            AutomationListScreen()
        }
    }
}
