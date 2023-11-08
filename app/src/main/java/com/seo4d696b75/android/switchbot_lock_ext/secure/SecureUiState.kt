package com.seo4d696b75.android.switchbot_lock_ext.secure

import androidx.compose.runtime.Immutable

@Immutable
sealed interface SecureUiState {
    data object Authenticated : SecureUiState

    data object NotAuthenticated : SecureUiState

    data object NoAuthenticator : SecureUiState

    data class AuthenticationError(
        val message: String,
    ) : SecureUiState
}
