package com.seo4d696b75.android.switchbot_lock_ext.api.auth.impl

import com.seo4d696b75.android.switchbot_lock_ext.api.auth.CurrentTimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class CurrentTimeProviderImpl @Inject constructor() : CurrentTimeProvider {
    override fun invoke(): Long {
        return System.currentTimeMillis()
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface CurrentTimeProviderModule {
    @Binds
    fun bindCurrentTimeProvider(impl: CurrentTimeProviderImpl): CurrentTimeProvider
}
