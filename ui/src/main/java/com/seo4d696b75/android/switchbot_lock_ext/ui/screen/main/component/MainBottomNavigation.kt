package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.main.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.seo4d696b75.android.switchbot_lock_ext.ui.navigation.currentBottomTab
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.Screen

@Composable
fun MainBottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val current = backStackEntry?.currentBottomTab

    BottomNavigation(
        modifier = modifier.fillMaxWidth(),
    ) {
        listOf(
            Screen.Home,
            Screen.Device,
            Screen.User,
        ).forEach { tab ->
            BottomNavigationItem(
                selected = current == tab,
                onClick = {
                    navController.navigate(tab.tabRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(tab.icon, contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = tab.labelId))
                },
            )
        }
    }
}
