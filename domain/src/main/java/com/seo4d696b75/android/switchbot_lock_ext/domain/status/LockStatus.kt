package com.seo4d696b75.android.switchbot_lock_ext.domain.status

sealed interface LockStatus {
    data class Data(
        val battery: Int,
        val version: String,
        val state: LockedState,
        val isDoorClosed: Boolean,
        val isCalibrated: Boolean,
    ) : LockStatus

    /**
     * No status available while fetching at the first time
     */
    data object Loading : LockStatus

    /**
     * No status available and an error happened while fetching
     */
    data object Error : LockStatus
}

sealed interface LockedState {
    data class Normal(
        val isLocked: Boolean,
        val isLoading: Boolean = false,
    ) : LockedState

    data object Jammed : LockedState

    /**
     * An error happened while updating 'locked' state,
     * and current state is ambiguous
     */
    data object Error : LockedState
}

interface LockStatusStore {
    operator fun get(id: String): LockStatus
}
