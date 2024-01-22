package com.pidzama.smokecrafthookahapp.data.model

import com.google.gson.annotations.SerializedName

data class Worker(
    val worker: ArrayList<WorkerItem> = arrayListOf()
)

data class WorkerItem(
    @SerializedName("created")
    val created: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_active")
    val is_active: Boolean,
    @SerializedName("organization")
    val organization: Int,
    @SerializedName("updated")
    val updated: String,
    @SerializedName("user")
    val user: User
)

data class User(
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val first_name: String,
    @SerializedName("last_name")
    val last_name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("user_role")
    val user_role: String,
    @SerializedName("username")
    val username: String
)