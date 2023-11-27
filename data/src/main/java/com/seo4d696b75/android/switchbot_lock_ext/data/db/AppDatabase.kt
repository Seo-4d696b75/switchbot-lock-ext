package com.seo4d696b75.android.switchbot_lock_ext.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.seo4d696b75.android.switchbot_lock_ext.data.storage.AppStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory

@Database(
    entities = [LockDeviceEntity::class, LockGeofenceEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lockDeviceDao(): LockDeviceDao
    abstract fun lockGeofenceDao(): LockGeofenceDao
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        storage: AppStorage,
    ): AppDatabase {
        val password =
            requireNotNull(storage.getDbPassword()).encodeToByteArray()
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "app_database",
        )
            .openHelperFactory(SupportOpenHelperFactory(password))
            .build()
    }
}
