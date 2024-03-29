package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.main.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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

    NavigationBar(
        modifier = modifier.fillMaxWidth(),
    ) {
        listOf(
            Screen.Home,
            Screen.Automation,
            Screen.Device,
            Screen.User,
        ).forEach { tab ->
            NavigationBarItem(
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
                    Icon(
                        painter = painterResource(id = tab.iconId),
                        contentDescription = null,
                    )
                },
                label = {
                    Text(text = stringResource(id = tab.labelId))
                },
            )
        }
    }
}
