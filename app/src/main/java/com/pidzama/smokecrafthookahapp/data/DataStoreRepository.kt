package com.pidzama.smokecrafthookahapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.pidzama.smokecrafthookahapp.data.DataStoreRepository.PreferencesKey.onBoardingKey
import com.pidzama.smokecrafthookahapp.utils.Constants.DataStorePreferences.AUTH_KEY
import com.pidzama.smokecrafthookahapp.utils.Constants.DataStorePreferences.DATA_STORE_NAME
import com.pidzama.smokecrafthookahapp.utils.Constants.DataStorePreferences.ON_BOARDING_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class DataStoreRepository(context: Context) {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = ON_BOARDING_KEY)
    }

    private val dataStore = context.dataStore

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[onBoardingKey] = completed
        }
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState =
                    preferences[booleanPreferencesKey(name = ON_BOARDING_KEY)] ?: false
                onBoardingState
            }
    }

    suspend fun saveAuthToken(loginToken:String){
        dataStore.edit { pref ->
            pref[AUTH_KEY] = setOf(loginToken)
        }
    }
}

