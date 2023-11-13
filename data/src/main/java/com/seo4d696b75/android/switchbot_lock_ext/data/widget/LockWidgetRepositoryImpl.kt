package com.seo4d696b75.android.switchbot_lock_ext.data.widget

import com.seo4d696b75.android.switchbot_lock_ext.domain.control.LockControlRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class LockWidgetRepositoryImpl @Inject constructor(
    private val controlRepository: LockControlRepository,
    private val appWidgetMediator: AppWidgetMediator,
) :
    LockWidgetRepository {

    private val stateMap = mutableMapOf<String, LockWidgetState>()

    override fun getState(deviceId: String): LockWidgetState {
        return stateMap[deviceId] ?: LockWidgetState.Idling
    }

    override suspend fun setLocked(deviceId: String, isLocked: Boolean) {
        withContext(Dispatchers.IO) {
            stateMap[deviceId] = LockWidgetState.Loading(isLocking = isLocked)
            appWidgetMediator.update()
            val result = runCatching {
                controlRepository.setLocked(deviceId, isLocked)
                isLocked
            }
            stateMap[deviceId] = LockWidgetState.Completed(result)
            appWidgetMediator.update()
        }
    }

    override fun setIdle(deviceId: String) {
        val current = getState(deviceId)
        stateMap[deviceId] = when (current) {
            is LockWidgetState.Completed -> LockWidgetState.Idling
            else -> current
        }
        appWidgetMediator.update()
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface LockWidgetRepositoryModule {
    @Binds
    @Singleton
    fun bindLockWidgetRepository(impl: LockWidgetRepositoryImpl): LockWidgetRepository
}
