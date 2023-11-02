package com.seo4d696b75.android.switchbot_lock_ext.domain.control

interface LockControlRepository {
    suspend fun setLocked(id: String, locked: Boolean)
}
