package com.pidzama.smokecrafthookahapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.pidzama.smokecrafthookahapp.utils.Constants.DataStorePreferences.AUTH_KEY
import com.pidzama.smokecrafthookahapp.utils.Constants.DataStorePreferences.DATA_STORE_NAME
import com.pidzama.smokecrafthookahapp.utils.Constants.DataStorePreferences.THEME_MODE
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class DataStoreRepository(context: Context) {

    private val dataStore = context.dataStore

    suspend fun saveAuthToken(loginToken: String) {
        dataStore.edit { pref ->
            pref[AUTH_KEY] = setOf(loginToken)
        }
    }

    suspend fun saveThemeMode(isDarkMode: Boolean) {
        dataStore.edit { pref ->
            pref[THEME_MODE] = isDarkMode
        }
    }

    fun getThemeDataStore() = dataStore.data.map { preferences ->
        val themeMode = preferences[THEME_MODE] ?: false
        themeMode
    }
}

