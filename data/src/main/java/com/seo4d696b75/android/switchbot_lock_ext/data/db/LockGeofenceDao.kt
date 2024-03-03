package com.seo4d696b75.android.switchbot_lock_ext.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

@Dao
interface LockGeofenceDao {
    @Query("SELECT * FROM lock_geofence_tb")
    fun getAll(): Flow<List<LockGeofenceEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun add(entity: LockGeofenceEntity)

    @Update
    suspend fun update(entity: LockGeofenceEntity)

    @Query("DELETE FROM lock_geofence_tb WHERE id in (:ids)")
    suspend fun remove(ids: List<String>)

    @Query("DELETE FROM lock_geofence_tb")
    suspend fun removeAll()
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object LockGeofenceDaoModule {
    @Provides
    fun provideLockGeofenceDao(db: AppDatabase): LockGeofenceDao =
        db.lockGeofenceDao()
}
