package com.seo4d696b75.android.switchbot_lock_ext.data.error

import android.util.Log
import com.seo4d696b75.android.switchbot_lock_ext.domain.error.ErrorHandler
import com.seo4d696b75.android.switchbot_lock_ext.domain.error.ErrorHolder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.PrintWriter
import java.io.StringWriter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException

class ErrorHandlerImpl @Inject constructor(
    private val holder: ErrorHolder,
) : ErrorHandler {
    override fun CoroutineScope.launchCatching(
        context: CoroutineContext,
        start: CoroutineStart,
        isError: MutableStateFlow<Boolean>?,
        isLoading: MutableStateFlow<Boolean>?,
        block: suspend CoroutineScope.() -> Unit
    ): Job = launch(
        context = context,
        start = start,
    ) {
        isError?.update { false }
        isLoading?.update { true }
        try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            enqueueThrowable(e)
            isError?.update { true }
        }
        isLoading?.update { false }
    }

    override fun <T> Flow<T>.stateInCatching(
        scope: CoroutineScope,
        started: SharingStarted,
        initialValue: T
    ): StateFlow<T> = retry { e ->
        enqueueThrowable(e)
        // wait until the error has been consumed
        holder.error.first { it == null }
        true
    }.stateIn(
        scope = scope,
        started = started,
        initialValue = initialValue,
    )

    override fun <T> Flow<T>.catchingError(
        isError: MutableStateFlow<Boolean>,
    ): Flow<T> = retry { e ->
        enqueueThrowable(e)
        isError.update { true }
        isError.first { !it }
        true
    }

    override fun enqueueThrowable(error: Throwable) {
        val stack = StringWriter().also {
            error.printStackTrace(PrintWriter(it))
        }.toString()
        Log.w("ErrorHolder", "${error.message}\n$stack")
        holder.enqueue(error)
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface ErrorHandlerModule {
    @Binds
    fun bind(impl: ErrorHandlerImpl): ErrorHandler
}
