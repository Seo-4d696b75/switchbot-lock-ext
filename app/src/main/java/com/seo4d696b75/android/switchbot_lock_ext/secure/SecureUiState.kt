package com.seo4d696b75.android.switchbot_lock_ext.secure

import androidx.compose.runtime.Immutable
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UIMessage

@Immutable
sealed interface SecureUiState {
    data object Authenticated : SecureUiState

    data object NotAuthenticated : SecureUiState

    data object NoAuthenticator : SecureUiState

    data class AuthenticationError(
        val message: UIMessage,
    ) : SecureUiState
}
