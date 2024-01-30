package com.pidzama.smokecrafthookahapp.data.model

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
class RandomRecipeSubList : ArrayList<RandomRecipeSubListItem>(), Parcelable

class ListTaskType : NavType<RandomRecipeSubList>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): RandomRecipeSubList? {
        return bundle.getParcelable(key)
    }
    override fun parseValue(value: String): RandomRecipeSubList {
        return Gson().fromJson(value, RandomRecipeSubList::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: RandomRecipeSubList) {
        bundle.putParcelable(key, value)
    }
}