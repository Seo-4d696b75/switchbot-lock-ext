package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.auth.UserCredential
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRepository
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val tokenInput = MutableStateFlow(TextFieldValue())
    private val secretInput = MutableStateFlow(TextFieldValue())
    private val onUserSavedFlow =
        MutableStateFlow<UiEvent<Unit>>(UiEvent.None)

    init {
        when (val user = userRepository.currentUser) {
            is UserRegistration.User -> {
                tokenInput.update { TextFieldValue(user.credential.token) }
                secretInput.update { TextFieldValue(user.credential.secret) }
            }

            else -> {}
        }
    }

    val uiState: StateFlow<UserUiState> = combine(
        userRepository.userFlow,
        tokenInput,
        secretInput,
        onUserSavedFlow,
    ) { user, token, secret, event ->
        UserUiState(
            user = user,
            tokenInput = token,
            secretInput = secret,
            isSaveEnabled = validateFormInput(token.text, secret.text),
            onUserSaved = event,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        UserUiState.InitialValue,
    )

    private fun validateFormInput(
        token: String,
        secret: String,
    ): Boolean {
        return token.isNotBlank() && secret.isNotBlank()
    }

    fun onTokenInputChanged(text: TextFieldValue) {
        tokenInput.update { text }
    }

    fun onSecretInputChanged(text: TextFieldValue) {
        secretInput.update { text }
    }

    fun saveUser() {
        viewModelScope.launch {
            val token = tokenInput.value.text
            val secret = secretInput.value.text
            if (validateFormInput(token, secret)) {
                userRepository.save(
                    UserRegistration.User(
                        credential = UserCredential(token, secret),
                    )
                )
                onUserSavedFlow.update { UiEvent.Data(Unit) }
            }
        }
    }

    fun removeUser() {
        userRepository.remove()
    }
}
