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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockedState
import com.seo4d696b75.android.switchbot_lock_ext.theme.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.common.AnimatedToggle

@Composable
fun LockToggle(
    state: LockedState.Normal,
    onLockedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lockedColor = MaterialTheme.colorScheme.primary
    val unlockedColor = MaterialTheme.colorScheme.secondary
    val contentColor = MaterialTheme.colorScheme.onPrimary
    val lockText = stringResource(id = R.string.label_lock)
    val unlockText = stringResource(id = R.string.label_unlock)
    val lockingText = stringResource(id = R.string.label_locking)
    val unlockingText = stringResource(id = R.string.label_unlocking)
    AnimatedToggle(
        modifier = modifier,
        checked = state.isLocked,
        loading = state.isLoading,
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
                text = if (it) unlockText else lockText,
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
                text = if (it) lockingText else unlockingText,
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
