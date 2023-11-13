package com.seo4d696b75.android.switchbot_lock_ext.ui.widget

import androidx.datastore.core.DataStore
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.widget.LockWidgetStateProvider

class LockWidgetDataStore(
    widgetRepository: LockWidgetRepository,
) : DataStore<LockWidgetStateProvider> {
    override val data = widgetRepository.stateProviderFlow

    override suspend fun updateData(
        transform: suspend (t: LockWidgetStateProvider) -> LockWidgetStateProvider,
    ): LockWidgetStateProvider {
        throw NotImplementedError()
    }
}
