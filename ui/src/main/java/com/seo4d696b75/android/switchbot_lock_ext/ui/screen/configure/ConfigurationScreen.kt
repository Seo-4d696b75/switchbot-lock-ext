package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.configure

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetType
import com.seo4d696b75.android.switchbot_lock_ext.ui.error.ErrorHandler
import com.seo4d696b75.android.switchbot_lock_ext.ui.navigation.navigateSingleTop
import com.seo4d696b75.android.switchbot_lock_ext.ui.navigation.typeMap
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.Screen
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.device.SelectDeviceScreen

@Composable
fun ConfigurationScreen(
    appWidgetType: AppWidgetType,
    appWidgetId: Int,
    onCompleted: () -> Unit,
) {
    val navController = rememberNavController()
    ErrorHandler()
    NavHost(
        navController = navController,
        modifier = Modifier.fillMaxSize(),
        startDestination = Screen.Configuration.Top(
            appWidgetType,
            appWidgetId,
        ),
        typeMap = typeMap,
    ) {
        composable<Screen.Configuration.Top>(typeMap) {
            WidgetConfigurationScreen(
                onCompleted = onCompleted,
                onSelectDeviceRequested = {
                    navController.navigateSingleTop(Screen.Configuration.SelectDevice)
                },
            )
        }

        composable<Screen.Configuration.SelectDevice>(typeMap) { backstack ->
            val owner = remember(backstack) {
                navController.getBackStackEntry<Screen.Configuration.Top>()
            }
            val viewModel: WidgetConfigurationViewModel = hiltViewModel(owner)
            SelectDeviceScreen(
                onCompleted = {
                    viewModel.onDeviceSelected(it)
                    navController.popBackStack()
                },
            )
        }
    }
}
