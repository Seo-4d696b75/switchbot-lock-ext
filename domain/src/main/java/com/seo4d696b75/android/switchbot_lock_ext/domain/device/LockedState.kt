package com.seo4d696b75.android.switchbot_lock_ext.domain.device

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
