package com.seo4d696b75.android.switchbot_lock_ext.data.db

import android.content.Context
import com.seo4d696b75.android.switchbot_lock_ext.data.storage.AppStorage
import com.seo4d696b75.android.switchbot_lock_ext.domain.initialize.AppInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import java.security.SecureRandom
import javax.inject.Inject

class AppDatabaseInitializer @Inject constructor(
    private val storage: AppStorage,
) : AppInitializer {
    override suspend fun invoke(context: Context) {
        System.loadLibrary("sqlcipher")
        val password = storage.getDbPassword()
        if (password == null) {
            val rand = SecureRandom.getInstanceStrong()
            val bytes = ByteArray(32)
            rand.nextBytes(bytes)
            val hex = bytes.joinToString("") { "%02x".format(it) }
            storage.saveDbPassword(hex)
        }
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface AppDatabaseInitializerModule {
    @Binds
    @IntoSet
    fun bindInitializer(impl: AppDatabaseInitializer): AppInitializer
}
