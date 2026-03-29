package com.tau.client.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsRepository(private val context: Context) {
    private val HOST_KEY = stringPreferencesKey("tau_host")
    private val TOKEN_KEY = stringPreferencesKey("tau_token")

    val tauHost: Flow<String> = context.dataStore.data.map { it[HOST_KEY] ?: "100.91.199.107" }
    val tauToken: Flow<String> = context.dataStore.data.map { it[TOKEN_KEY] ?: "fjrtsale-secure-2026" }

    suspend fun saveSettings(host: String, token: String) {
        context.dataStore.edit {
            it[HOST_KEY] = host
            it[TOKEN_KEY] = token
        }
    }
}
