package com.seo4d696b75.android.switchbot_lock_ext.data.storage

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppStorage @Inject constructor(
    private val preferences: SharedPreferences,
) {

    fun getUserToken(): String? = preferences.getString(KEY_USER_TOKEN, null)

    fun saveUserToken(value: String) = preferences
        .edit()
        .putString(KEY_USER_TOKEN, value)
        .apply()

    fun removeUserToken() = preferences
        .edit()
        .remove(KEY_USER_TOKEN)
        .apply()

    fun getUserSecret(): String? = preferences.getString(KEY_USER_SECRET, null)

    fun saveUserSecret(value: String) = preferences
        .edit()
        .putString(KEY_USER_SECRET, value)
        .apply()

    fun removeUserSecret() = preferences
        .edit()
        .remove(KEY_USER_SECRET)
        .apply()

    fun getDbPassword(): String? = preferences.getString(KEY_DATABASE_PASSWORD, null)

    fun saveDbPassword(value: String) = preferences
        .edit()
        .putString(KEY_DATABASE_PASSWORD, value)
        .apply()

    companion object {
        private const val KEY_USER_TOKEN = "pref_key_user_token"
        private const val KEY_USER_SECRET = "pref_key_user_secret"
        private const val KEY_DATABASE_PASSWORD = "pref_key_database_password"
    }
}
