package com.manan.test.kotlinnews.data.entity

import com.google.gson.annotations.SerializedName

data class Reddit (

    @SerializedName("kind")
    private val kind: String? = null,
    @SerializedName("data")
    internal val listing: Listing? = null

)