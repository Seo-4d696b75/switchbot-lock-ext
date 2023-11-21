package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.PrimaryButton
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user.component.CredentialsDescription
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme

@Composable
fun EditUserPage(
    tokenText: TextFieldValue,
    secretText: TextFieldValue,
    isSaveButtonEnabled: Boolean,
    onTokenChanged: (TextFieldValue) -> Unit,
    onSecretChanged: (TextFieldValue) -> Unit,
    onSaveUserClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp,
            ),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = tokenText,
                    onValueChange = onTokenChanged,
                    singleLine = true,
                    label = {
                        Text(text = stringResource(id = R.string.label_user_token))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(
                            onClick = { onTokenChanged(TextFieldValue()) },
                        ) {
                            Icon(
                                Icons.Outlined.Clear,
                                contentDescription = stringResource(id = R.string.label_clear_input),
                            )
                        }
                    },
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = secretText,
                    onValueChange = onSecretChanged,
                    singleLine = true,
                    label = {
                        Text(text = stringResource(id = R.string.label_user_secret))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(
                            onClick = { onSecretChanged(TextFieldValue()) },
                        ) {
                            Icon(
                                Icons.Outlined.Clear,
                                contentDescription = stringResource(id = R.string.label_clear_input),
                            )
                        }
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))

                CredentialsDescription()
            }
        }
        PrimaryButton(
            label = stringResource(id = R.string.label_save),
            enabled = isSaveButtonEnabled,
            onClick = onSaveUserClicked,
            iconResId = R.drawable.ic_save,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun EditUserPagePreview() {
    AppTheme {
        Surface {
            EditUserPage(
                tokenText = TextFieldValue("token"),
                secretText = TextFieldValue("password"),
                isSaveButtonEnabled = true,
                onTokenChanged = {},
                onSecretChanged = {},
                onSaveUserClicked = {},
            )
        }
    }
}
