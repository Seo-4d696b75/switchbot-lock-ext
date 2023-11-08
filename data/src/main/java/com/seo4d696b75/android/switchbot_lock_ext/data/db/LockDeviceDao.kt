package com.seo4d696b75.android.switchbot_lock_ext.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

@Dao
interface LockDeviceDao {
    @Query("SELECT * FROM lock_device_tb")
    fun getAllDevices(): Flow<List<LockDeviceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDevices(devices: List<LockDeviceEntity>)

    @Query("DELETE FROM lock_device_tb WHERE id = :id")
    suspend fun removeDevice(id: String)
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object LockDeviceDaoModule {
    @Provides
    fun provideLockDeviceDao(db: AppDatabase): LockDeviceDao =
        db.lockDeviceDao()
}
