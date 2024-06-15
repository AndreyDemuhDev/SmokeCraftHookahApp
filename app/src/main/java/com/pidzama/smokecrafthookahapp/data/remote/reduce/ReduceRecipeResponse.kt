package com.pidzama.smokecrafthookahapp.data.remote.reduce


data class ReduceRecipeResponse(
    val reduced: List<ReduceRecipeRequest>,
    val failed: List<String>,
)