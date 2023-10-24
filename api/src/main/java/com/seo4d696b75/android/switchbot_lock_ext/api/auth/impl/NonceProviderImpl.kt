package com.seo4d696b75.android.switchbot_lock_ext.api.auth.impl

import com.seo4d696b75.android.switchbot_lock_ext.api.auth.NonceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

class NonceProviderImpl @Inject constructor() : NonceProvider {

    private val value = UUID.randomUUID().toString()

    override fun invoke(): String {
        return value
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface NonceProviderModule {
    @Binds
    @Singleton
    fun bindNonceProvider(impl: NonceProviderImpl): NonceProvider
}
