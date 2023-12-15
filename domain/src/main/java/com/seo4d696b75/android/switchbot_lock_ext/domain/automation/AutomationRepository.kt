package com.seo4d696b75.android.switchbot_lock_ext.domain.automation

import kotlinx.coroutines.flow.Flow

interface AutomationRepository {
    val automationFlow: Flow<List<LockAutomation>>
    suspend fun addAutomation(automation: LockAutomation): String
    suspend fun updateAutomation(automation: LockAutomation)
    suspend fun removeAutomation(automation: LockAutomation)
}
