package com.seo4d696b75.android.switchbot_lock_ext.ui.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import kotlinx.serialization.Serializable

/**
 * navigationの全routeを定義
 */
sealed interface Screen {

    /**
     * BottomNavigationに対応させるタブ
     */
    sealed interface BottomTab {
        @get:StringRes
        val labelId: Int

        @get:DrawableRes
        val iconId: Int
    }

    sealed interface Main : Screen

    sealed interface Home : Main {
        @Serializable
        data object Top : Home

        @Serializable
        data class StatusDetailDialog(
            val deviceId: String,
        ) : Home

        @Serializable
        data object Tab : BottomTab {
            override val labelId = R.string.bottom_nav_home
            override val iconId = R.drawable.ic_home
        }
    }

    sealed interface User : Main {
        @Serializable
        data object Top : User

        @Serializable
        data object Edit : User

        @Serializable
        data object Tab : BottomTab {
            override val labelId = R.string.bottom_nav_user
            override val iconId = R.drawable.ic_person
        }
    }
}
