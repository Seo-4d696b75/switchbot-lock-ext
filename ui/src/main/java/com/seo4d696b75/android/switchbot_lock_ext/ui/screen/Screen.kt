package com.seo4d696b75.android.switchbot_lock_ext.ui.screen

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.seo4d696b75.android.switchbot_lock_ext.ui.R

/**
 * navigationの全routeを定義
 */
sealed interface Screen {
    val route: String

    /**
     * BottomNavigationに対応させるタブ
     */
    sealed interface BottomTab {
        val tabRoute: String
        @get:StringRes
        val labelId: Int
        val icon: ImageVector
    }

    sealed interface Home : Screen {
        data object Top : Home {
            override val route = "$tabRoute/top"
        }

        companion object : BottomTab {
            override val tabRoute = "home"
            override val labelId = R.string.bottom_nav_home
            override val icon = Icons.Outlined.Home
        }
    }

    sealed interface Device : Screen {
        data object Top : Device {
            override val route = "$tabRoute/top"
        }

        companion object : BottomTab {
            override val tabRoute = "device"
            override val labelId = R.string.bottom_nav_device
            override val icon = Icons.Outlined.Lock
        }
    }

    sealed interface User : Screen {
        data object Top : User {
            override val route = "$tabRoute/top"
        }

        companion object : BottomTab {
            override val tabRoute = "user"
            override val labelId = R.string.bottom_nav_user
            override val icon = Icons.Outlined.Person
        }
    }
}
