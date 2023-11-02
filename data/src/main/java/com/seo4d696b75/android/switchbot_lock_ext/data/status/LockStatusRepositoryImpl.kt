package com.seo4d696b75.android.switchbot_lock_ext.data.status

import android.util.Log
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRemoteRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.device.DeviceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.AsyncLockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatusRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatusStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
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

class LockStatusRepositoryImpl @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val remoteRepository: DeviceRemoteRepository,
) : LockStatusRepository {

    private var runningJob: Job? = null
    private val dirtyFlagFlow = MutableStateFlow(true)

    override val statusFlow: Flow<LockStatusStore> = channelFlow {
        combine(
            deviceRepository
                .controlDeviceFlow
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
    }.map(::LockStatusStoreImpl)

    override fun refresh() {
        dirtyFlagFlow.update { true }
    }

    private class LockStatusStoreImpl(
        private val map: Map<String, Result<LockStatus>>,
    ) : LockStatusStore {
        override fun get(id: String): AsyncLockStatus {
            return map[id]?.let { result ->
                result.getOrNull()?.let { status ->
                    AsyncLockStatus.Data(status)
                } ?: AsyncLockStatus.Error
            } ?: AsyncLockStatus.Loading
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