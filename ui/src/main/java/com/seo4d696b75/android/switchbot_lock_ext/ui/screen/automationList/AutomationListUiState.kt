package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.automationList

import androidx.compose.runtime.Immutable
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.LockGeofence
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class AutomationListUiState(
    val user: UserRegistration,
    val automations: ImmutableList<LockGeofence>,
    val isLoading: Boolean,
) {
    companion object {
        val InitialValue = AutomationListUiState(
            user = UserRegistration.Loading,
            automations = persistentListOf(),
            isLoading = false,
        )
    }
}
