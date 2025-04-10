package com.seo4d696b75.android.switchbot_lock_ext.domain.async

import com.seo4d696b75.android.switchbot_lock_ext.domain.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlin.coroutines.cancellation.CancellationException


interface AsyncValueProvider<T : Any> {
    operator fun invoke(): Flow<AsyncValue<T>>
    suspend fun refresh()
}

fun <T : Any> ErrorHandler.asyncValueOf(
    upstream: Flow<T?>,
    refresh: suspend () -> Unit,
): AsyncValueProvider<T> = AsyncValueProviderImpl(upstream, refresh, this)

private class AsyncValueProviderImpl<T : Any>(
    private val upstream: Flow<T?>,
    private val refreshLambda: suspend () -> Unit,
    private val handler: ErrorHandler,
) : AsyncValueProvider<T> {

    private val error = MutableStateFlow<Throwable?>(null)
    private val isError = MutableStateFlow(false)
    private val isLoading = MutableStateFlow(false)

    override fun invoke(): Flow<AsyncValue<T>> {
        return combine(
            error,
            isLoading,
            upstream.retry { e ->
                handler.enqueueThrowable(e)
                error.update { e }
                isError.update { true }
                isError.first { !it }
                true
            },
        ) { error, isLoading, data ->
            if (data != null) {
                AsyncValue.Data(
                    data = data,
                    isLoading = isLoading,
                    error = error,
                )
            } else if (error != null && !isLoading) {
                AsyncValue.Error(error)
            } else {
                AsyncValue.Loading(error)
            }
        }
    }

    override suspend fun refresh() {
        isError.update { false }
        isLoading.update { true }
        try {
            refreshLambda()
        } catch (e: Throwable) {
            if (e is CancellationException) {
                throw e
            } else {
                error.update { e }
                isError.update { true }
            }
        } finally {
            isLoading.update { false }
        }
    }
}
