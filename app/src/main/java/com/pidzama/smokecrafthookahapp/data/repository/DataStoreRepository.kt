package com.pidzama.smokecrafthookahapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.pidzama.smokecrafthookahapp.utils.Constants.DataStorePreferences.AUTH_KEY
import com.pidzama.smokecrafthookahapp.utils.Constants.DataStorePreferences.DATA_STORE_NAME
import com.pidzama.smokecrafthookahapp.utils.Constants.DataStorePreferences.THEME_MODE
import com.pidzama.smokecrafthookahapp.utils.Constants.DataStorePreferences.USER_LOGIN
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class DataStoreRepository(context: Context) {

    private val dataStore = context.dataStore
    suspend fun saveAuthToken(loginToken: String) {
        dataStore.edit { pref ->
            pref[AUTH_KEY] = setOf(loginToken)
        }
    }

    fun getAuthToken() = dataStore.data.map { preferences ->
        val authToken = preferences[AUTH_KEY] ?: ""
        authToken
    }

    suspend fun saveUserLogin(loginUser: String) {
        dataStore.edit { pref ->
            pref[USER_LOGIN] = setOf(loginUser)
        }
    }

    fun getUserLogin() = dataStore.data.map { preferences ->
        val loginUser = preferences[USER_LOGIN] ?: ""
        loginUser
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

