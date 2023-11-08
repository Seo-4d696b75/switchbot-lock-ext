package com.seo4d696b75.android.switchbot_lock_ext.domain.user

import com.seo4d696b75.android.switchbot_lock_ext.domain.auth.UserCredential

sealed interface UserRegistration {
    data object Loading : UserRegistration
    data object Undefined : UserRegistration

    data class User(
        val credential: UserCredential,
    ) : UserRegistration
}
