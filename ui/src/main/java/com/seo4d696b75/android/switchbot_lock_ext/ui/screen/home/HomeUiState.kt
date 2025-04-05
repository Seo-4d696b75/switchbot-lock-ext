package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home

import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import kotlinx.collections.immutable.ImmutableList

data class HomeUiState(
    val user: UserRegistration,
    val devices: ImmutableList<LockStatus>?,
) {
    companion object {
        val InitialValue = HomeUiState(
            user = UserRegistration.Loading,
            devices = null,
        )
    }
}
