package com.pidzama.smokecrafthookahapp.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

class Constants {

    object DataStorePreferences {
        const val DATA_STORE_NAME = "data_store_pref"
        const val AUTH_PREFERENCES = "AUTH_PREF"
        val AUTH_KEY = stringSetPreferencesKey("auth_key")
        val USER_LOGIN = stringSetPreferencesKey("user_login")
        val THEME_MODE = booleanPreferencesKey("theme_mode")
    }

    object Network {
        const val BASE_URL = "http://188.68.221.107/"
    }

    object TastyWeight {
        private const val firstTasty = 12f
        private const val secondTasty = 6f
        private const val threeTasty = 2f
        val ListTastyWeight =  listOf(firstTasty, secondTasty, threeTasty)

    }

    object Database {
        const val NAME_DATABASE = "recipe_data_base"
    }
}