package com.seo4d696b75.android.switchbot_lock_ext.widget.simpleLock

import kotlinx.serialization.Serializable

@Serializable
sealed interface SimpleLockWidgetStatus {
    @Serializable
    data object Idling : SimpleLockWidgetStatus

    sealed interface Loading : SimpleLockWidgetStatus {
        val isLocking: Boolean?
    }

    @Serializable
    data object FetchingCurrent : Loading {
        override val isLocking = null
    }

    @Serializable
    data class SendingCommand(
        override val isLocking: Boolean,
    ) : Loading

    @Serializable
    data class Success(val isLocked: Boolean) : SimpleLockWidgetStatus

    @Serializable
    data class Failure(val message: String) : SimpleLockWidgetStatus
}
