package com.pidzama.smokecrafthookahapp.data.remote.reduce

import com.google.gson.annotations.SerializedName

data class ReduceRecipeResponse(
    @SerializedName("message")
    val result: String
)