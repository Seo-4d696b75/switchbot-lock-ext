package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.AppLockedSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.PrimaryButton

@Composable
fun NoAuthenticatorScreen(
    navigateToSetting: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            AppLockedSection(
                description = stringResource(id = R.string.description_no_authenticator),
            )
        }
        PrimaryButton(
            label = stringResource(id = R.string.label_configure_lock_screen),
            onClick = navigateToSetting,
        )
    }
}
