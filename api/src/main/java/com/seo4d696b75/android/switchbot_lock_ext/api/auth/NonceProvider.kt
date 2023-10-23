package com.seo4d696b75.android.switchbot_lock_ext.api.auth

interface NonceProvider {
    operator fun invoke(): String
}
