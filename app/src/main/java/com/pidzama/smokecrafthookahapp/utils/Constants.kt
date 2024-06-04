package com.pidzama.smokecrafthookahapp.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

class Constants {

    object DataStorePreferences {
        const val DATA_STORE_NAME = "data_store_pref"
        const val DATA_STORE_TOKEN = "data_store_token"
        const val AUTH_PREFERENCES = "AUTH_PREF"
        val AUTH_KEY = stringSetPreferencesKey("auth_key")
        val USER_LOGIN = stringSetPreferencesKey("user_login")
        val THEME_MODE = booleanPreferencesKey("theme_mode")
    }

    object Network {
        const val BASE_URL = "http://158.160.130.206/"
    }

    object Tasty {
//        private const val firstTasty = 12f
//        private const val secondTasty = 6f
//        private const val threeTasty = 2f
//        val ListTastyWeight =  listOf(firstTasty, secondTasty, threeTasty)
        const val bowlVolume = 20
    }

    object Database {
        const val NAME_DATABASE = "recipe_data_base"
    }
}