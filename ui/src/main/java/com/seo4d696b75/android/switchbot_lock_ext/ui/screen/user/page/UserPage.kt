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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.auth.UserCredential
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.PrimaryButton
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user.component.CredentialsDescription
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Token：",
                        modifier = Modifier.width(80.dp),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = user.credential.token,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Secret：",
                        modifier = Modifier.width(80.dp),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "●".repeat(user.credential.secret.length),
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                CredentialsDescription()
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            PrimaryButton(
                label = "Delete user",
                onClick = onRemoveUserClicked,
                iconResId = R.drawable.ic_delete,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            PrimaryButton(
                label = "Edit user",
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