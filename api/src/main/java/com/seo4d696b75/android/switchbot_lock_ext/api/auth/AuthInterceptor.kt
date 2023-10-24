package com.seo4d696b75.android.switchbot_lock_ext.api.auth

import com.seo4d696b75.android.switchbot_lock_ext.domain.auth.UserCredentialProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val getCurrentTime: CurrentTimeProvider,
    private val getNonce: NonceProvider,
    private val getCredential: UserCredentialProvider,
    private val generateSign: AuthSignGenerator,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val credential = getCredential()
        val timestamp = getCurrentTime()
        val nonce = getNonce()
        val sign = generateSign(timestamp, nonce, credential)
        val newRequest = chain.request()
            .newBuilder()
            .header("Authorization", credential.token)
            .header("t", timestamp.toString())
            .header("nonce", nonce)
            .header("sign", sign)
            .header("charset", "utf8")
            .header("Content-Type", "application/json")
            .build()
        return chain.proceed(newRequest)
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface AuthInterceptorModule {
    @Binds
    @IntoSet
    fun bindAuthInterceptor(impl: AuthInterceptor): Interceptor
}
