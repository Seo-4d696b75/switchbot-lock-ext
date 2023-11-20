package com.seo4d696b75.android.switchbot_lock_ext.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
sealed interface UiEvent<out T> {
    data object None : UiEvent<Nothing> {
        override fun runOnce(block: (Nothing) -> Unit) {
            // nothing to do
        }
    }

    data class Data<T>(
        val value: T,
        private var hasConsumed: Boolean = false,
    ) : UiEvent<T> {
        override fun runOnce(block: (T) -> Unit) {
            if (!hasConsumed) {
                hasConsumed = true
                block(value)
            }
        }
    }

    fun runOnce(block: (T) -> Unit)
}

@Composable
fun <T> ObserveEvent(
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
