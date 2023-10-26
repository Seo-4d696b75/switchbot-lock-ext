package com.seo4d696b75.android.switchbot_lock_ext.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Singleton
    @Provides
    fun providePreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences {
        val spec = MasterKeys.AES256_GCM_SPEC
        val key = MasterKeys.getOrCreate(spec)
        return EncryptedSharedPreferences.create(
            "app_preferences_secured",
            key,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }
}
