package com.seo4d696b75.android.switchbot_lock_ext.domain.auth

interface UserCredentialProvider {
    operator fun invoke(): UserCredential
}
