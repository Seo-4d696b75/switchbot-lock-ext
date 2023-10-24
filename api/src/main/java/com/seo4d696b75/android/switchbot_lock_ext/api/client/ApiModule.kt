package com.seo4d696b75.android.switchbot_lock_ext.api.client

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideSwitchBotApi(retrofit: Retrofit): SwitchBotApi =
        retrofit.create(SwitchBotApi::class.java)
}
