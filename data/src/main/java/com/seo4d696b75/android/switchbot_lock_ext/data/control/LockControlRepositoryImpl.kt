package com.seo4d696b75.android.switchbot_lock_ext.data.control

import com.seo4d696b75.android.switchbot_lock_ext.api.client.SwitchBotApi
import com.seo4d696b75.android.switchbot_lock_ext.api.request.command.DeviceCommandRequest
import com.seo4d696b75.android.switchbot_lock_ext.domain.control.LockControlRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class LockControlRepositoryImpl @Inject constructor(
    private val api: SwitchBotApi,
) : LockControlRepository {
    override suspend fun setLocked(id: String, locked: Boolean) {
        val request = DeviceCommandRequest(
            command = if (locked) "lock" else "unlock",
        )
        api.postCommand(id, request)
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface LockControlRepositoryModule {
    @Binds
    fun bindLockControlRepository(impl: LockControlRepositoryImpl): LockControlRepository
}
