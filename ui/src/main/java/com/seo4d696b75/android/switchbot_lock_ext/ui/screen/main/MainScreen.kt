package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.seo4d696b75.android.switchbot_lock_ext.ui.navigation.deviceNavGraph
import com.seo4d696b75.android.switchbot_lock_ext.ui.navigation.homeNavGraph
import com.seo4d696b75.android.switchbot_lock_ext.ui.navigation.userNavGraph
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.Screen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.main.component.MainBottomNavigation

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            MainBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            startDestination = Screen.Home.tabRoute,
        ) {
            homeNavGraph(navController)
            deviceNavGraph(navController)
            userNavGraph(navController)
        }
    }
}
