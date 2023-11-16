package com.seo4d696b75.android.switchbot_lock_ext.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

@Dao
interface LockDeviceDao {
    @Query("SELECT * FROM lock_device_tb")
    fun getAllDevices(): Flow<List<LockDeviceEntity>>

    @Insert
    suspend fun addDevices(devices: List<LockDeviceEntity>)

    @Query("DELETE FROM lock_device_tb")
    suspend fun removeAll()

    @Transaction
    suspend fun refreshDevices(devices: List<LockDeviceEntity>) {
        removeAll()
        addDevices(devices)
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object LockDeviceDaoModule {
    @Provides
    fun provideLockDeviceDao(db: AppDatabase): LockDeviceDao =
        db.lockDeviceDao()
}
