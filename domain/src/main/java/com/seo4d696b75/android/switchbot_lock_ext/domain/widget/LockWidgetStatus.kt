package com.seo4d696b75.android.switchbot_lock_ext.domain.widget

import kotlinx.serialization.Serializable

@Serializable
sealed interface LockWidgetStatus {
    @Serializable
    data object Idling : LockWidgetStatus

    @Serializable
    data class Loading(val isLocking: Boolean) : LockWidgetStatus

    @Serializable
    data class Success(val isLocked: Boolean) : LockWidgetStatus

    @Serializable
    data class Failure(val message: String) : LockWidgetStatus
}
