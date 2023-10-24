package com.seo4d696b75.android.switchbot_lock_ext.api.client

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.seo4d696b75.android.switchbot_lock_ext.api.auth.AuthInterceptor
import com.seo4d696b75.android.switchbot_lock_ext.api.response.ConvertResponseInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.time.Duration

class RetrofitFactory(
    private val authInterceptor: AuthInterceptor,
) {
    fun create(): Retrofit {
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(authInterceptor)
            .addNetworkInterceptor(ConvertResponseInterceptor())
            .addNetworkInterceptor(HttpLoggingInterceptor())
            .connectTimeout(Duration.ofSeconds(3))
            .readTimeout(Duration.ofSeconds(3))
            .retryOnConnectionFailure(false)
            .build()
        val json = Json { ignoreUnknownKeys = true }
        val mediaType = "application/json".toMediaType()
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.switch-bot.com/")
            .addConverterFactory(json.asConverterFactory(mediaType))
            .build()
    }
}
