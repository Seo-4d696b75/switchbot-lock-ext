package com.seo4d696b75.android.switchbot_lock_ext.ui.common

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Stable
interface NavEvent

interface NavEventPublisher<E : NavEvent> {
    val event: SharedFlow<E>
}

@Composable
fun <E : NavEvent> ObserveEvent(
    publisher: NavEventPublisher<E>,
    collector: FlowCollector<E>,
) {
    val callback by rememberUpdatedState(collector)
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(publisher) {
        Log.d("NavEvent", "LaunchedEffect")
        publisher
            .event
            .flowWithLifecycle(lifecycle)
            .collect {
                callback.emit(it)
            }
    }
}

@Stable
sealed interface UiEvent<out T> {
    data object None : UiEvent<Nothing>

    data class Data<T>(
        val value: T,
        var hasConsumed: Boolean = false,
    ) : UiEvent<T>

    fun runOnce(block: (T) -> Unit) {
        when (this) {
            None -> {}
            is Data<T> -> {
                if (!hasConsumed) {
                    hasConsumed = true
                    block(value)
                }
            }
        }
    }
}

@Composable
fun <T> LaunchedEvent(
    event: UiEvent<T>,
    block: suspend CoroutineScope.(T) -> Unit,
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(event) {
        event.runOnce {
            scope.launch { block(it) }
        }
    }
}
