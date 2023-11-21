package com.seo4d696b75.android.switchbot_lock_ext.secure


import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.UIMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SecureViewModel @Inject constructor() : ViewModel() {

    private val _uiState =
        MutableStateFlow<SecureUiState>(SecureUiState.NotAuthenticated)
    val uiState = _uiState.asStateFlow()

    private val authCallback =
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                _uiState.update { SecureUiState.Authenticated }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                _uiState.update {
                    SecureUiState.AuthenticationError(
                        UIMessage.ResId(R.string.message_user_auth_fialed),
                    )
                }
            }

            override fun onAuthenticationError(
                errorCode: Int,
                errString: CharSequence
            ) {
                super.onAuthenticationError(errorCode, errString)
                _uiState.update {
                    SecureUiState.AuthenticationError(
                        UIMessage.Raw("$errString (code: $errorCode)"),
                    )
                }
            }
        }

    fun lockApp() {
        _uiState.update { SecureUiState.NotAuthenticated }
    }

    fun authenticate(
        activity: FragmentActivity,
        title: String,
        subTitle: String,
    ) {
        _uiState.update { SecureUiState.NotAuthenticated }
        val manager = BiometricManager.from(activity)
        when (val result = manager.canAuthenticate(allowedAuthenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val executor = ContextCompat.getMainExecutor(activity)
                val prompt = BiometricPrompt(activity, executor, authCallback)
                val info = BiometricPrompt.PromptInfo.Builder()
                    .setTitle(title)
                    .setSubtitle(subTitle)
                    .setAllowedAuthenticators(allowedAuthenticators)
                    .build()
                prompt.authenticate(info)
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Log.d("secure", "no authenticator found")
                _uiState.update { SecureUiState.NoAuthenticator }
            }

            else -> {
                _uiState.update {
                    Log.d("secure", "unexpected error: $result")
                    SecureUiState.AuthenticationError(
                        UIMessage.ResId(R.string.message_user_auth_fialed),
                    )
                }
            }
        }
    }

    companion object {
        const val allowedAuthenticators =
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
    }
}
