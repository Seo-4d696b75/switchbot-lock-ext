package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.auth.UserCredential
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.PrimaryButton
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user.component.CredentialsDescription

@Composable
fun UserPage(
    user: UserRegistration.User,
    onEditUserClicked: () -> Unit,
    onRemoveUserClicked: () -> Unit,
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
                Image(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp),
                    colorFilter = ColorFilter.tint(Color.Gray),
                )
                Text(
                    text = stringResource(
                        R.string.message_user_token,
                        user.credential.token,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = stringResource(
                        R.string.message_user_secret,
                        "●".repeat(user.credential.secret.length),
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                )

                Spacer(modifier = Modifier.height(24.dp))

                CredentialsDescription()
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            PrimaryButton(
                label = stringResource(id = R.string.label_delete_user),
                onClick = onRemoveUserClicked,
                iconResId = R.drawable.ic_delete,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            PrimaryButton(
                label = stringResource(id = R.string.label_edit_user),
                onClick = onEditUserClicked,
                iconResId = R.drawable.ic_edit,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview
@Composable
private fun UserPagePreview() {
    AppTheme {
        Surface {
            UserPage(
                user = UserRegistration.User(
                    credential = UserCredential(
                        token = "jieapopoveapoiohnioamoewioohveaionveaihvae",
                        secret = "iopnmbvoraijifea",
                    ),
                ),
                onEditUserClicked = {},
                onRemoveUserClicked = {},
            )
        }
    }
}
