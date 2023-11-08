package com.seo4d696b75.android.switchbot_lock_ext.secure


import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
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
                    SecureUiState.AuthenticationError("Authentication failed")
                }
            }

            override fun onAuthenticationError(
                errorCode: Int,
                errString: CharSequence
            ) {
                super.onAuthenticationError(errorCode, errString)
                _uiState.update {
                    SecureUiState.AuthenticationError("Error code: $errorCode ($errString)")
                }
            }
        }

    fun authenticate(activity: FragmentActivity) {
        _uiState.update { SecureUiState.NotAuthenticated }
        val manager = BiometricManager.from(activity)
        when (manager.canAuthenticate(allowedAuthenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val executor = ContextCompat.getMainExecutor(activity)
                val prompt = BiometricPrompt(activity, executor, authCallback)
                val info = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("User Authentication")
                    .setSubtitle("For security of your credentials, authentication is required before using this app.")
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
                    SecureUiState.AuthenticationError("unexpected error")
                }
            }
        }
    }

    companion object {
        val allowedAuthenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.DEVICE_CREDENTIAL
    }
}
