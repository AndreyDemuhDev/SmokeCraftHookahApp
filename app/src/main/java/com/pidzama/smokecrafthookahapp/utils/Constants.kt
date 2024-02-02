package com.pidzama.smokecrafthookahapp.utils

import androidx.datastore.preferences.core.stringSetPreferencesKey

class Constants {

    object DataStorePreferences {
        const val DATA_STORE_NAME = "data_store_pref"
        const val ON_BOARDING_KEY = "on_boarding_key"
        const val AUTH_PREFERENCES = "AUTH_PREF"
        val AUTH_KEY = stringSetPreferencesKey("auth_key")
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
}