package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration

@Immutable
data class UserUiState(
    val user: UserRegistration,
    val tokenInput: TextFieldValue,
    val secretInput: TextFieldValue,
) {
    companion object {
        val InitialValue = UserUiState(
            user = UserRegistration.Undefined,
            tokenInput = TextFieldValue(),
            secretInput = TextFieldValue(),
        )
    }
}
