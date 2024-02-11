package com.pidzama.smokecrafthookahapp.data.common.base

interface Mapper<F, T> {

    fun fromMap(from: F): T
}