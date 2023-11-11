package com.seo4d696b75.android.switchbot_lock_ext.api.client

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.time.Duration

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    fun provideRetrofit(
        interceptors: MutableSet<Interceptor>,
    ): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(Duration.ofSeconds(3))
            .readTimeout(Duration.ofSeconds(20))
            .retryOnConnectionFailure(false).apply {
                interceptors.forEach {
                    addNetworkInterceptor(it)
                }
            }
            .build()
        val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
        val mediaType = "application/json".toMediaType()
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.switch-bot.com/")
            .addConverterFactory(json.asConverterFactory(mediaType))
            .build()
    }
}
