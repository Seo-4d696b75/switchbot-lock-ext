package com.seo4d696b75.android.switchbot_lock_ext.domain.status

sealed interface AsyncLockStatus {
    data class Data(
        val data: LockStatus,
    ) : AsyncLockStatus

    data object Loading : AsyncLockStatus

    data object Error : AsyncLockStatus
}

interface LockStatusStore {
    operator fun get(id: String) : AsyncLockStatus
}
