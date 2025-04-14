package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R

@Composable
fun BatterySection(
    percent: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_battery_4),
            contentDescription = null,
            tint = if (percent < 10) {
                Color(0xFFFE2020)
            } else if (percent < 20) {
                Color(0xFFFFC72A)
            } else {
                Color(0xFF21BE50)
            },
            modifier = Modifier.size(32.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(R.string.message_battery_percent, percent),
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

private class BatteryParamProvider : PreviewParameterProvider<Int> {
    override val values = sequenceOf(5, 15, 20, 90)
}

@Preview
@Composable
private fun BatterySectionPreview(
    @PreviewParameter(BatteryParamProvider::class) percent: Int,
) {
    AppTheme {
        Surface {
            BatterySection(
                percent = percent,
                modifier = Modifier.width(100.dp),
            )
        }
    }
}
