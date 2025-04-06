package com.seo4d696b75.android.switchbot_lock_ext.data.error

import com.seo4d696b75.android.switchbot_lock_ext.domain.error.ErrorHolder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHolderImpl @Inject constructor() : ErrorHolder {

    private val _error = MutableStateFlow<Throwable?>(null)

    override val error = _error.asStateFlow()

    override fun enqueue(error: Throwable) {
        _error.update { error }
    }

    override fun consume() {
        _error.update { null }
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface ErrorHolderModule {
    @Binds
    fun bind(impl: ErrorHolderImpl): ErrorHolder
}
