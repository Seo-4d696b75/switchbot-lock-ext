package com.seo4d696b75.android.switchbot_lock_ext.domain.status

import kotlinx.coroutines.flow.Flow

interface LockStatusRepository {
    val statusFlow: Flow<LockStatusStore>
    fun refresh()
}
