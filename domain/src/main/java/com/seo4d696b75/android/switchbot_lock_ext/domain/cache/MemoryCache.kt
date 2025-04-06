package com.seo4d696b75.android.switchbot_lock_ext.domain.cache

import kotlinx.coroutines.flow.Flow

interface MemoryCache<T : Any> {
    operator fun invoke(): Flow<T?>
    fun invalidate()
    suspend fun refresh(): T
}
