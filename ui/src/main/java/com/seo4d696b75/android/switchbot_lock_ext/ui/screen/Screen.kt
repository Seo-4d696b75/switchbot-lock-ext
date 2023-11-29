package com.seo4d696b75.android.switchbot_lock_ext.ui.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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

        @get:DrawableRes
        val iconId: Int
    }

    sealed interface Home : Screen {
        data object Top : Home {
            override val route = "$tabRoute/top"
        }

        data object StatusDetailDialog : Home {
            override val route = "$tabRoute/statusDetailDialog/{deviceId}"
            fun createRoute(id: String) = "$tabRoute/statusDetailDialog/$id"
        }

        companion object : BottomTab {
            override val tabRoute = "home"
            override val labelId = R.string.bottom_nav_home
            override val iconId = R.drawable.ic_home
        }
    }

    sealed interface Automation : Screen {
        data object List : Automation {
            override val route = "$tabRoute/list"
        }

        companion object : BottomTab {
            override val tabRoute = "automation"
            override val labelId = R.string.bottom_nav_automation
            override val iconId = R.drawable.ic_rocket
        }
    }

    sealed interface Device : Screen {
        data object List : Device {
            override val route = "$tabRoute/list"
        }

        companion object : BottomTab {
            override val tabRoute = "device"
            override val labelId = R.string.bottom_nav_device
            override val iconId = R.drawable.ic_lock
        }
    }

    sealed interface User : Screen {
        data object Top : User {
            override val route = "$tabRoute/top"
        }

        data object Edit : User {
            override val route = "$tabRoute/edit"
        }

        companion object : BottomTab {
            override val tabRoute = "user"
            override val labelId = R.string.bottom_nav_user
            override val iconId = R.drawable.ic_person
        }
    }
}
