package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home

import com.seo4d696b75.android.switchbot_lock_ext.domain.async.AsyncValue
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration

data class HomeUiState(
    val user: UserRegistration,
    val devices: AsyncValue<List<LockStatus>>,
) {
    companion object {
        val InitialValue = HomeUiState(
            user = UserRegistration.Loading,
            devices = AsyncValue.Loading(),
        )
    }
}
