package com.seo4d696b75.android.switchbot_lock_ext.api.auth.impl

import android.util.Base64
import com.seo4d696b75.android.switchbot_lock_ext.api.auth.AuthSignGenerator
import com.seo4d696b75.android.switchbot_lock_ext.domain.auth.UserCredential
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class AuthSignGeneratorImpl @Inject constructor() : AuthSignGenerator {
    override fun invoke(
        timestamp: Long,
        nonce: String,
        credential: UserCredential
    ): String {
        val data = "${credential.token}$timestamp$nonce"
        val bytes = data.encodeToByteArray()
        val key = credential.secret.encodeToByteArray()
        val sign = Mac.getInstance("HmacSHA256").run {
            init(SecretKeySpec(key, "HmacSHA256"))
            doFinal(bytes)
        }
        return Base64.encodeToString(sign, Base64.NO_WRAP)
    }

}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface AuthSingGeneratorModule {
    @Binds
    fun bindAuthSignGenerator(impl: AuthSignGeneratorImpl): AuthSignGenerator
}
