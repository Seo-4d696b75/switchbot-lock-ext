package com.seo4d696b75.android.switchbot_lock_ext.api.response

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import javax.inject.Inject

class ConvertResponseInterceptor @Inject constructor() : Interceptor {

    private val json = Json { ignoreUnknownKeys = true }
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        return if (response.code == 200) {
            val body = requireNotNull(response.body)
            val model: SwitchBotApiResponse =
                json.decodeFromString(body.string())
            if (model.statusCode != 100) {
                throw SwitchBotApiStatusException(model.statusCode)
            }
            val newBody =
                model.body.toString().toResponseBody(body.contentType())
            response.newBuilder()
                .body(newBody)
                .build()
        } else {
            response
        }
    }
}

class SwitchBotApiStatusException(
    statusCode: Int,
) : IOException("unexpected statusCode: $statusCode")

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface ConvertResponseInterceptorModule {
    @Binds
    @IntoSet
    fun bindConvertResponseInterceptor(impl: ConvertResponseInterceptor): Interceptor
}
