package com.seo4d696b75.android.switchbot_lock_ext.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.theme.AppTheme
import com.seo4d696b75.android.switchbot_lock_ext.theme.R

@Composable
fun PrimaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes
    iconResId: Int? = null,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(48.dp),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 4.dp,
        ),
    ) {
        if (iconResId != null) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = label,
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = label,
            //modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PrimaryButtonPreview() {
    AppTheme {
        PrimaryButton(
            label = "Button",
            onClick = { },
            iconResId = R.drawable.ic_lock,
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PrimaryButtonPreview_Disabled() {
    AppTheme {
        PrimaryButton(
            label = "Button",
            onClick = { },
            iconResId = R.drawable.ic_lock,
            enabled = false,
        )
    }
}
