package com.seo4d696b75.android.switchbot_lock_ext.api.log

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object LoggingInterceptorModule {
    @Provides
    @IntoSet
    fun provideLogInterceptor(): Interceptor {
        return HttpLoggingInterceptor {
            Log.d("okHttp", it)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}
