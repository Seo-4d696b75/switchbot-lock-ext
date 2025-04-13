package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.configure.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockDevice
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.PrimaryButton
import com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.component.previewDevice
import kotlin.math.roundToInt

@Composable
fun WidgetConfigurationPage(
    device: LockDevice?,
    opacity: Float,
    onOpacityChanged: (Float) -> Unit,
    isCompletable: Boolean,
    complete: () -> Unit,
    selectDevice: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_lock),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.size(32.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.title_widget_device),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = selectDevice,
                modifier = Modifier.widthIn(max = 360.dp),
            ) {
                Text(
                    text = device?.name
                        ?: stringResource(R.string.placeholder_select_device),
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_gradient),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.size(32.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.title_widget_opacity) + " ${(opacity * 100).roundToInt()}%",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.widthIn(max = 360.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.label_transparent),
                    style = MaterialTheme.typography.labelLarge,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Slider(
                    value = opacity,
                    onValueChange = onOpacityChanged,
                    valueRange = 0.25f..1f,
                    steps = 2,
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.label_not_transparent),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }

        PrimaryButton(
            label = stringResource(R.string.label_complete),
            onClick = complete,
            enabled = isCompletable,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

@Preview
@Composable
private fun WidgetConfigurationPagePreview() {
    AppTheme {
        Surface {
            var opacity by remember { mutableFloatStateOf(1f) }
            WidgetConfigurationPage(
                device = previewDevice,
                opacity = opacity,
                onOpacityChanged = { opacity = it },
                isCompletable = false,
                complete = {},
                selectDevice = {},
            )
        }
    }
}
