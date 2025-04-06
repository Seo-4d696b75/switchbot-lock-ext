package com.seo4d696b75.android.switchbot_lock_ext.data.status

import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRemoteRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.LockedState
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatusRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LockStatusRepositoryImpl @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val remoteRepository: DeviceRemoteRepository,
) : LockStatusRepository {

    private val lockStateCacheFlow =
        MutableStateFlow<Map<String, LockedState>>(emptyMap())

    private val isRefresh = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val remoteStatusFlow = combine(
        deviceRepository.deviceFlow,
        isRefresh,
    ) { list, isRefreshing ->
        list to isRefreshing
    }.transformLatest { (list, isRefreshing) ->
        if (list == null) {
            emit(null)
        } else if (isRefreshing) {
            // maintain previous value (no emit)
        } else {
            // clear cache
            lockStateCacheFlow.update { emptyMap() }

            emit(
                list.map { LockStatus.Loading(it) }
            )

            // fetch latest status
            val result = coroutineScope {
                list.map { device ->
                    async {
                        runCatching {
                            remoteRepository.getLockDeviceStatus(
                                device.id
                            )
                        }.getOrNull()?.let {
                            LockStatus.Data(
                                device = device,
                                state = it,
                            )
                        } ?: LockStatus.Error(device)
                    }
                }.awaitAll()
            }
            emit(result)
        }
    }

    override val statusFlow = combine(
        remoteStatusFlow,
        lockStateCacheFlow,
    ) { remote, cache ->
        remote?.map { status ->
            when (status) {
                is LockStatus.Data -> cache[status.device.id]?.let {
                    // 最新の LockedState を反映する
                    status.copy(
                        state = status.state.copy(locked = it),
                    )
                } ?: status

                is LockStatus.Loading,
                is LockStatus.Error -> status
            }
        }
    }

    override suspend fun refresh() {
        try {
            isRefresh.update { true }
            deviceRepository.refresh()
        } finally {
            isRefresh.update { false }
        }
    }

    override fun update(id: String, state: LockedState) {
        lockStateCacheFlow.update {
            it.toMutableMap().apply { set(id, state) }
        }
    }
}

@Suppress("unused")
@Module
@InstallIn(ActivityRetainedComponent::class)
interface LockStatusRepositoryModule {
    @Binds
    @ActivityRetainedScoped
    fun bindLockStatusRepository(impl: LockStatusRepositoryImpl): LockStatusRepository
}
