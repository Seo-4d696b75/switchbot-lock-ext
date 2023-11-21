package com.seo4d696b75.android.switchbot_lock_ext.ui.common

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource

@Immutable
sealed interface UIMessage {
    data class Raw(val value: String) : UIMessage

    data class ResId(@StringRes val id: Int) : UIMessage
}

@Composable
fun uiMessage(message: UIMessage): String {
    return when (message) {
        is UIMessage.Raw -> message.value
        is UIMessage.ResId -> stringResource(id = message.id)
    }
}
