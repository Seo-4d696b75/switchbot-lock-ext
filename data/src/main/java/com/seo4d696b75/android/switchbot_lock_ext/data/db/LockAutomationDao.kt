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
interface LockAutomationDao {
    @Query("SELECT * FROM lock_automation_tb")
    fun getAll(): Flow<List<LockAutomationEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun add(entity: LockAutomationEntity)

    @Update
    suspend fun update(entity: LockAutomationEntity)

    @Query("DELETE FROM lock_automation_tb WHERE id in (:ids)")
    suspend fun remove(ids: List<String>)

    @Query("DELETE FROM lock_automation_tb")
    suspend fun removeAll()
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object LockAutomationDaoModule {
    @Provides
    fun provideAutomationDao(db: AppDatabase): LockAutomationDao =
        db.lockAutomationDao()
}
