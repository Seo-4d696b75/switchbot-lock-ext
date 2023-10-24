package com.seo4d696b75.android.switchbot_lock_ext.api.auth

import com.seo4d696b75.android.switchbot_lock_ext.domain.auth.UserCredential

interface AuthSignGenerator {
    operator fun invoke(
        timestamp: Long,
        nonce: String,
        credential: UserCredential,
    ): String
}
