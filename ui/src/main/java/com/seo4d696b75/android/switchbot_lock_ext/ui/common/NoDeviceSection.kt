package com.seo4d696b75.android.switchbot_lock_ext.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R

@Composable
fun NoDeviceSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_lock),
            contentDescription = null,
            modifier = Modifier.size(160.dp),
            colorFilter = ColorFilter.tint(Color.Gray),
        )
        Text(
            text = stringResource(id = R.string.title_no_devices),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.description_device_controllable),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun NoDeviceSectionPreview() {
    AppTheme {
        Surface {
            NoDeviceSection()
        }
    }
}
