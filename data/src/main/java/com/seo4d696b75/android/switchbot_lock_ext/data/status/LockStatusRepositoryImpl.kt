package com.seo4d696b75.android.switchbot_lock_ext.data.status

import android.util.Log
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRemoteRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatusRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatusStore
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockedState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

class LockStatusRepositoryImpl @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val remoteRepository: DeviceRemoteRepository,
) : LockStatusRepository {

    private var runningJob: Job? = null
    private val dirtyFlagFlow = MutableStateFlow(true)

    private val lockStateCacheFlow =
        MutableStateFlow<Map<String, LockedState>>(emptyMap())

    private val statusMapFlow = channelFlow {
        combine(
            deviceRepository
                .deviceFlow
                .map { devices -> devices.map { it.id } }
                .distinctUntilChanged(),
            dirtyFlagFlow,
        ) { ids, dirty ->
            ids to dirty
        }.distinctUntilChanged { old, new ->
            // ignore when dirtyFlag changed true > false
            old.second && !new.second
        }.collect { pair ->
            val (ids, dirty) = pair
            Log.d("data", "LockStatusRepositoryImpl ids: $ids dirty: $dirty")
            if (dirty) {
                send(emptyMap())
            }
            runningJob?.cancel()
            runningJob = launch {
                val result = ids.map { id ->
                    async {
                        runCatching {
                            remoteRepository.getLockDeviceStatus(id)
                        }
                    }
                }.awaitAll()
                val map = ids.zip(result).toMap()
                send(map)
                dirtyFlagFlow.update { false }
            }
        }
    }

    override val statusFlow: Flow<LockStatusStore> = combine(
        statusMapFlow,
        lockStateCacheFlow,
    ) { map, lockStateCache ->
        LockStatusStoreImpl(map, lockStateCache)
    }

    override fun refresh() {
        dirtyFlagFlow.update { true }
        lockStateCacheFlow.update { emptyMap() }
    }

    override fun update(id: String, state: LockedState) {
        lockStateCacheFlow.update {
            it.toMutableMap().apply { set(id, state) }
        }
    }

    private class LockStatusStoreImpl(
        private val map: Map<String, Result<LockStatus.Data>>,
        private val lockStateCache: Map<String, LockedState>,
    ) : LockStatusStore {
        override fun get(id: String): LockStatus {
            return map[id]?.let { result ->
                result.getOrNull()?.let { status ->
                    lockStateCache[id]?.let { state ->
                        status.copy(state = state)
                    } ?: status
                } ?: LockStatus.Error
            } ?: LockStatus.Loading
        }
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface LockStatusRepositoryModule {
    @Binds
    @Singleton
    fun bindLockStatusRepository(impl: LockStatusRepositoryImpl): LockStatusRepository
}
