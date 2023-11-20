package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.ObserveEvent
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user.page.EditUserPage

@Composable
fun EditUserScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveEvent(uiState.onUserSaved) {
        navigateBack()
    }

    EditUserScreen(
        tokenText = uiState.tokenInput,
        secretText = uiState.secretInput,
        isSaveButtonEnabled = uiState.isSaveEnabled,
        onTokenChanged = viewModel::onTokenInputChanged,
        onSecretChanged = viewModel::onSecretInputChanged,
        onSaveUserClicked = viewModel::saveUser,
        modifier = modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScreen(
    tokenText: TextFieldValue,
    secretText: TextFieldValue,
    isSaveButtonEnabled: Boolean,
    onTokenChanged: (TextFieldValue) -> Unit,
    onSecretChanged: (TextFieldValue) -> Unit,
    onSaveUserClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Edit User")
                },
            )
        },
    ) { innerPadding ->
        EditUserPage(
            modifier = Modifier.padding(innerPadding),
            tokenText = tokenText,
            secretText = secretText,
            isSaveButtonEnabled = isSaveButtonEnabled,
            onTokenChanged = onTokenChanged,
            onSecretChanged = onSecretChanged,
            onSaveUserClicked = onSaveUserClicked,
        )
    }
}
