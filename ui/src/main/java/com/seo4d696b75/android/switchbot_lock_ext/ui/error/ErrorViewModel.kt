package com.seo4d696b75.android.switchbot_lock_ext.ui.error

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seo4d696b75.android.switchbot_lock_ext.domain.error.ErrorHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ErrorViewModel @Inject constructor(
    private val holder: ErrorHolder,
) : ViewModel() {
    val state = holder
        .error
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null,
        )

    fun dismiss() {
        holder.consume()
    }
}
