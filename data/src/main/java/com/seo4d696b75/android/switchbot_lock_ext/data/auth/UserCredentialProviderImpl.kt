package com.seo4d696b75.android.switchbot_lock_ext.data.auth

import com.seo4d696b75.android.switchbot_lock_ext.domain.auth.UserCredential
import com.seo4d696b75.android.switchbot_lock_ext.domain.auth.UserCredentialProvider
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class UserCredentialProviderImpl @Inject constructor(
    private val userRepository: UserRepository,
) :
    UserCredentialProvider {
    override fun invoke(): UserCredential {
        return when (val user = userRepository.currentUser) {
            is UserRegistration.User -> user.credential
            UserRegistration.Undefined -> throw IllegalStateException("user credentials not found")
        }
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface UserCredentialProviderModule {
    @Binds
    fun bindUserCredentialProvider(impl: UserCredentialProviderImpl): UserCredentialProvider
}
