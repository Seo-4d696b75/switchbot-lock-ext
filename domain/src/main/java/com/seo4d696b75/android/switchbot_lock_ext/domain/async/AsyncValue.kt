package com.seo4d696b75.android.switchbot_lock_ext.domain.async

sealed interface AsyncValue<T : Any> {
    val data: T?
    val isLoading: Boolean
    val error: Throwable?

    /**
     * Initial loading (without data)
     */
    data class Loading<T : Any>(
        override val error: Throwable? = null,
    ) : AsyncValue<T> {
        override val data = null
        override val isLoading = true
    }

    /**
     * Initial error (without data)
     */
    data class Error<T : Any>(
        override val error: Throwable,
    ) : AsyncValue<T> {
        override val data = null
        override val isLoading = false
    }

    /**
     * Has any data
     */
    data class Data<T : Any>(
        override val data: T,
        override val isLoading: Boolean = false,
        override val error: Throwable? = null,
    ) : AsyncValue<T>

    fun update(data: T) = Data(
        data = data,
        isLoading = false,
        error = null,
    )

    fun refresh() = when (this) {
        is Loading -> this
        is Error -> Loading(this.error)
        is Data -> this.copy(isLoading = true)
    }

    fun error(error: Throwable) = when (this) {
        is Loading -> Error(error)
        is Error -> Error(error)
        is Data -> this.copy(
            isLoading = false,
            error = error,
        )
    }
}
