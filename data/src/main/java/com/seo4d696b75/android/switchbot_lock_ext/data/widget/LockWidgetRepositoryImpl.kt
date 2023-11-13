package com.seo4d696b75.android.switchbot_lock_ext.data.widget

import com.seo4d696b75.android.switchbot_lock_ext.domain.control.LockControlRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.AppWidgetMediator
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetState
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetStateProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class LockWidgetRepositoryImpl @Inject constructor(
    private val controlRepository: LockControlRepository,
    private val appWidgetMediator: AppWidgetMediator,
) :
    LockWidgetRepository {

    private val stateMapFlow = MutableStateFlow<Map<String, LockWidgetState>>(
        emptyMap(),
    )

    override val stateProviderFlow: Flow<LockWidgetStateProvider> =
        stateMapFlow.map(::LockWidgetStateProviderImpl)

    private class LockWidgetStateProviderImpl constructor(
        private val map: Map<String, LockWidgetState>,
    ) : LockWidgetStateProvider {
        override fun get(deviceId: String) =
            map[deviceId] ?: LockWidgetState.Idling
    }

    override suspend fun setLocked(deviceId: String, isLocked: Boolean) {
        withContext(Dispatchers.IO) {
            stateMapFlow.update {
                it.toMutableMap().apply {
                    this[deviceId] =
                        LockWidgetState.Loading(isLocking = isLocked)
                }
            }
            appWidgetMediator.update()
            val result = runCatching {
                controlRepository.setLocked(deviceId, isLocked)
                isLocked
            }
            stateMapFlow.update {
                it.toMutableMap().apply {
                    this[deviceId] = LockWidgetState.Completed(result)
                }
            }
            appWidgetMediator.update()
        }
    }

    override fun setIdle(deviceId: String) {
        stateMapFlow.update {
            it.toMutableMap().apply {
                val current = this[deviceId] ?: LockWidgetState.Idling
                this[deviceId] = when (current) {
                    is LockWidgetState.Completed -> LockWidgetState.Idling
                    else -> current
                }
            }
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
