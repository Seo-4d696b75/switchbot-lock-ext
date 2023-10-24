package com.seo4d696b75.android.switchbot_lock_ext.data.auth

import com.seo4d696b75.android.switchbot_lock_ext.data.BuildConfig
import com.seo4d696b75.android.switchbot_lock_ext.domain.auth.UserCredential
import com.seo4d696b75.android.switchbot_lock_ext.domain.auth.UserCredentialProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class UserCredentialProviderImpl @Inject constructor() :
    UserCredentialProvider {
    override fun invoke(): UserCredential {
        return UserCredential(
            token = BuildConfig.SWITCH_BOT_TOKEN,
            secret = BuildConfig.SWITCH_BOT_SECRET,
        )
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface UserCredentialProviderModule {
    @Binds
    fun bindUserCredentialProvider(impl: UserCredentialProviderImpl): UserCredentialProvider
}
