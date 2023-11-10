package com.seo4d696b75.android.switchbot_lock_ext.ui.widget

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.preferencesOf
import androidx.glance.GlanceId
import androidx.glance.LocalContext
import androidx.glance.appwidget.ExperimentalGlanceRemoteViewsApi
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import com.google.android.glance.appwidget.host.AppWidgetHost
import com.google.android.glance.appwidget.host.glance.GlanceAppWidgetHostPreview
import com.google.android.glance.appwidget.host.glance.compose
import com.google.android.glance.appwidget.host.rememberAppWidgetHostState
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.AsyncLockStatus
import com.seo4d696b75.android.switchbot_lock_ext.domain.status.LockStatusRepository
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppWidgetTheme
import com.seo4d696b75.android.switchbot_lock_ext.ui.widget.component.LockControlSection
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class LockWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val entryPoint = EntryPointAccessors.fromApplication(
            context,
            LockWidgetEntryPoint::class.java,
        )
        val id = "E8893CF06602"
        provideContent {
            AppWidgetTheme {
                val statusStore by entryPoint
                    .statusRepository
                    .statusFlow
                    .collectAsState(initial = null)
                val status = statusStore?.get(id) ?: AsyncLockStatus.Loading
                LockControlSection(
                    name = "Lock",
                    status = status,
                    onLockedChanged = {},
                )
            }
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface LockWidgetEntryPoint {
        val statusRepository: LockStatusRepository
    }
}

@OptIn(ExperimentalGlanceRemoteViewsApi::class)
@Composable
@Preview
fun LockWidgetPreview() {
    val size = DpSize(100.dp, 100.dp)
    val state = preferencesOf()
    GlanceAppWidgetHostPreview(
        glanceAppWidget = LockWidget(),
        state = state,
        displaySize = size,
    )
}

@OptIn(ExperimentalGlanceRemoteViewsApi::class)
@Composable
fun LockWidgetScreen(widget: LockWidget) {
    val state = rememberAppWidgetHostState()
    val size = DpSize(100.dp, 100.dp)
    val context = LocalContext.current

    if (state.isReady) {
        LaunchedEffect(state.value) {
            state.updateAppWidget(
                widget.compose(context, size)
            )
        }
    }
    AppWidgetHost(
        modifier = Modifier.fillMaxSize(),
        displaySize = size,
        state = state,
    )
}
