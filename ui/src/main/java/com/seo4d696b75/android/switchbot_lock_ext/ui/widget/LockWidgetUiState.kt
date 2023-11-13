package com.seo4d696b75.android.switchbot_lock_ext.ui.widget

import androidx.compose.runtime.Immutable
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetState

@Immutable
sealed interface LockWidgetUiState {
    data object Idling : LockWidgetUiState
    data class Loading(val isLocking: Boolean) : LockWidgetUiState
    data class CommandSuccess(val isLocked: Boolean) : LockWidgetUiState
    data object CommandFailure : LockWidgetUiState

    companion object {
        fun fromModel(model: LockWidgetState) = when (model) {
            LockWidgetState.Idling -> Idling
            is LockWidgetState.Loading -> Loading(model.isLocking)
            is LockWidgetState.Completed -> model.isLocked.getOrNull()?.let {
                CommandSuccess(it)
            } ?: CommandFailure
        }
    }
}
