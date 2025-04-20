package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.seo4d696b75.android.switchbot_lock_ext.ui.error.ErrorHandler
import com.seo4d696b75.android.switchbot_lock_ext.ui.navigation.homeNavGraph
import com.seo4d696b75.android.switchbot_lock_ext.ui.navigation.userNavGraph
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.Screen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.main.component.MainBottomNavigation

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    ErrorHandler()
    Scaffold(
        bottomBar = {
            MainBottomNavigation(navController = navController)
        },
        modifier = modifier,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.Tab,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(WindowInsets.navigationBars),
        ) {
            homeNavGraph(navController)
            userNavGraph(navController)
        }
    }
}
