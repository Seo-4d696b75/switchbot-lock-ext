package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.auth.UserCredential
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val tokenInput = MutableStateFlow(TextFieldValue())
    private val secretInput = MutableStateFlow(TextFieldValue())

    val uiState: StateFlow<UserUiState> = combine(
        userRepository.userFlow,
        tokenInput,
        secretInput,
    ) { user, token, secret ->
        UserUiState(
            user = user,
            tokenInput = token,
            secretInput = secret,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        UserUiState.InitialValue,
    )

    fun onTokenInputChanged(text: TextFieldValue) {
        tokenInput.update { text }
    }

    fun onSecretInputChanged(text: TextFieldValue) {
        secretInput.update { text }
    }

    fun saveUser() {
        val token = tokenInput.value.text
        val secret = secretInput.value.text
        userRepository.save(
            UserRegistration.User(
                credential = UserCredential(token, secret),
            )
        )
        // TODO navigate popBack
    }

    fun removeUser() {
        userRepository.remove()
    }
}
