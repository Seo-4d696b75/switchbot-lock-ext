package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.user.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.NoUserSection
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.PrimaryButton

@Composable
fun NoUserPage(
    onAddUserClicked: () -> Unit,
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
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            NoUserSection()
        }
        PrimaryButton(
            label = stringResource(id = R.string.label_add_user),
            onClick = onAddUserClicked,
            iconResId = R.drawable.ic_add_person,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun NoUserPagePreview() {
    AppTheme {
        Surface {
            NoUserPage(onAddUserClicked = {})
        }
    }
}
