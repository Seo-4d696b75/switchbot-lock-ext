package com.seo4d696b75.android.switchbot_lock_ext.domain.user

import kotlinx.coroutines.flow.Flow


interface UserRepository {
    val currentUser: UserRegistration
    val userFlow: Flow<UserRegistration>
    fun save(user: UserRegistration.User)
    fun remove()
}
