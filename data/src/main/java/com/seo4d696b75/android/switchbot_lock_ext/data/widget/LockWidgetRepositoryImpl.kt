package com.seo4d696b75.android.switchbot_lock_ext.data.widget

import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetState
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetStateProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

class LockWidgetRepositoryImpl @Inject constructor() : LockWidgetRepository {

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

    override fun setState(deviceId: String, state: LockWidgetState) {
        stateMapFlow.update {
            it.toMutableMap().apply {
                this[deviceId] = state
            }
        }
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
