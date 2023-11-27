package com.seo4d696b75.android.switchbot_lock_ext.domain.initialize

import android.content.Context

fun interface AppInitializer {
    suspend operator fun invoke(context: Context)
}
