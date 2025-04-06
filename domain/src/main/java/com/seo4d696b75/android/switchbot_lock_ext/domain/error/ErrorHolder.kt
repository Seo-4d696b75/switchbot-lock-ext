package com.seo4d696b75.android.switchbot_lock_ext.domain.error

import kotlinx.coroutines.flow.Flow

interface ErrorHolder {
    val error: Flow<Throwable?>
    fun enqueue(error: Throwable)
    fun consume()
}
