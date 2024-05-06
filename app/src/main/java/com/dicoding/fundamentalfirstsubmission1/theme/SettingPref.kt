package com.dicoding.fundamentalfirstsubmission1.theme

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.prefDataStore by preferencesDataStore("settings")

class SettingPref(private val context: Context) {

    private val settingsDataStore = context.prefDataStore
    private val themeKEY = booleanPreferencesKey("theme_setting")

    private var data: Boolean = false // Initialize with a default value (false)

    fun getThemeSetting(): Flow<Boolean> =
        settingsDataStore.data.map { preferences ->
            preferences[themeKEY] ?: data // Use default value if key is not present
        }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingsDataStore.edit { preferences ->
            preferences[themeKEY] = isDarkModeActive
            data = isDarkModeActive // Update internal state
        }
    }
}