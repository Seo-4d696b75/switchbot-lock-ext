package com.seo4d696b75.android.switchbot_lock_ext.widget.component

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.glance.LocalContext

@Composable
fun glanceString(@StringRes id: Int): String {
    return LocalContext.current.getString(id)
}
