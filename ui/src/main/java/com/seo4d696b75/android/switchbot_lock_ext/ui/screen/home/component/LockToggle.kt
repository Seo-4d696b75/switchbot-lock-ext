package com.seo4d696b75.android.switchbot_lock_ext.ui.screen.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.AnimatedToggle

@Composable
fun LockToggle(
    isLocked: Boolean,
    onLockedChanged: suspend (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lockedColor = MaterialTheme.colorScheme.primary
    val unlockedColor = MaterialTheme.colorScheme.secondary
    val contentColor = MaterialTheme.colorScheme.onPrimary
    AnimatedToggle(
        modifier = modifier,
        checked = isLocked,
        label = {
            if (it) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = contentColor,
                )
            }
            Text(
                text = if (it) "Unlock" else "Lock",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = contentColor,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            if (!it) {
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = contentColor,
                )
            }
        },
        loadingLabel = {
            Text(
                text = if (it) "Locking..." else "Unlocking...",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = contentColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Clip,
            )
        },
        icon = {
            Icon(
                painter = painterResource(
                    id = if (it) R.drawable.ic_lock else R.drawable.ic_unlock,
                ),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = if (it) lockedColor else unlockedColor,
            )
        },
        contentColor = { if (it) lockedColor else unlockedColor },
        onCheckedChange = onLockedChanged,
        thumbColor = contentColor,
    )
}
