package com.manan.test.kotlinnews.data.entity

import com.google.gson.annotations.SerializedName

data class Thing (
    @SerializedName("kind")
    val kind: String? = null,
    @SerializedName("data")
    val data: SubReddit? = null
)