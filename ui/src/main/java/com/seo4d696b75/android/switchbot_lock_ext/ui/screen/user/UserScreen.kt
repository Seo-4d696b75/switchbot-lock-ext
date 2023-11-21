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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user.page.NoUserPage
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user.page.UserPage

@Composable
fun UserScreen(
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    UserScreen(
        modifier = modifier.fillMaxSize(),
        user = uiState.user,
        onEditUserClicked = navigateToEdit,
        onRemoveUserClicked = viewModel::removeUser,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    user: UserRegistration,
    onEditUserClicked: () -> Unit,
    onRemoveUserClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.top_bar_user))
                },
            )
        },
    ) { innerPadding ->
        when (user) {
            is UserRegistration.User -> {
                UserPage(
                    user = user,
                    onEditUserClicked = onEditUserClicked,
                    onRemoveUserClicked = onRemoveUserClicked,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            UserRegistration.Undefined -> {
                NoUserPage(
                    onAddUserClicked = onEditUserClicked,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            UserRegistration.Loading -> {}
        }
    }
}
