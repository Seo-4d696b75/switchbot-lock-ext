package com.seo4d696b75.android.switchbot_lock_ext.data.user

import com.seo4d696b75.android.switchbot_lock_ext.data.BuildConfig
import com.seo4d696b75.android.switchbot_lock_ext.data.storage.AppStorage
import com.seo4d696b75.android.switchbot_lock_ext.domain.auth.UserCredential
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRegistration
import com.seo4d696b75.android.switchbot_lock_ext.domain.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

class UserRepositoryImpl @Inject constructor(
    private val storage: AppStorage,
) : UserRepository {

    private val _userFlow =
        MutableStateFlow<UserRegistration>(UserRegistration.Loading)

    init {
        val token = storage.getUserToken()
        val secret = storage.getUserSecret()
        _userFlow.update {
            if (token != null && secret != null) {
                UserRegistration.User(
                    credential = UserCredential(token, secret),
                )
            } else {
                // UserRegistration.Undefined
                // TODO remove default credentials
                UserRegistration.User(
                    credential = UserCredential(
                        token = BuildConfig.SWITCH_BOT_TOKEN,
                        secret = BuildConfig.SWITCH_BOT_SECRET,
                    ),
                )
            }
        }
    }

    override val currentUser: UserRegistration
        get() = _userFlow.value

    override val userFlow = _userFlow.asStateFlow()

    override fun save(user: UserRegistration.User) {
        storage.saveUserToken(user.credential.token)
        storage.saveUserSecret(user.credential.secret)
        _userFlow.update { user }
    }

    override fun remove() {
        storage.removeUserToken()
        storage.removeUserSecret()
        _userFlow.update { UserRegistration.Undefined }
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface UserRepositoryModule {
    @Singleton
    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
