package com.pidzama.smokecrafthookahapp.data.remote

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("auth_token")
    val auth_token: String
)
