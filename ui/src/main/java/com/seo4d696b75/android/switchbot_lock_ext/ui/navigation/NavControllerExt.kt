package com.seo4d696b75.android.switchbot_lock_ext.ui.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavType
import androidx.navigation.toRoute
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetType
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.Screen
import kotlin.reflect.KType
import kotlin.reflect.typeOf

fun NavBackStackEntry.toRoute(): Screen = when {
    destination.hasRoute<Screen.Home.Top>() -> Screen.Home.Top
    destination.hasRoute<Screen.Home.StatusDetailDialog>() -> toRoute<Screen.Home.StatusDetailDialog>()
    destination.hasRoute<Screen.Home.SelectWidgetTypeDialog>() -> toRoute<Screen.Home.SelectWidgetTypeDialog>()
    destination.hasRoute<Screen.User.Top>() -> Screen.User.Top
    destination.hasRoute<Screen.User.Edit>() -> Screen.User.Edit
    destination.hasRoute<Screen.Configuration.Top>() -> toRoute<Screen.Configuration.Top>()
    destination.hasRoute<Screen.Configuration.SelectDevice>() -> Screen.Configuration.SelectDevice
    else -> throw IllegalStateException()
}

fun Screen.toTab(): Screen.BottomTab? = when (this) {
    is Screen.Home -> Screen.Home.Tab
    is Screen.User -> Screen.User.Tab
    else -> null
}

fun NavController.navigateSingleTop(route: Screen) {
    navigate(route) {
        launchSingleTop = true
    }
}

val typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = mapOf(
    typeOf<AppWidgetType>() to NavType.EnumType(AppWidgetType::class.java),
)
