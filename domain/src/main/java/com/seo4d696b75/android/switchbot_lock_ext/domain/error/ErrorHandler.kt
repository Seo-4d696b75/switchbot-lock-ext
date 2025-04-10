package com.seo4d696b75.android.switchbot_lock_ext.domain.error

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface ErrorHandler {
    /**
     * launch and run [block] with error catching.
     *
     * @param block call [enqueueThrowable] if any error has been thrown.
     */
    fun CoroutineScope.launchCatching(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ): Job

    /**
     * `stateIn` with error catching.
     *
     * call [enqueueThrowable] if any error from the original flow.
     * Collecting the flow will be restarted when the error is consumed.
     */
    fun <T> Flow<T>.stateInCatching(
        scope: CoroutineScope,
        started: SharingStarted,
        initialValue: T,
    ): StateFlow<T>

    /**
     * Show error message
     */
    fun enqueueThrowable(error: Throwable)
}
