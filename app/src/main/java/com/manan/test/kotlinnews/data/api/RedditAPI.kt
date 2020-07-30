package com.manan.test.kotlinnews.data.api

import com.manan.test.kotlinnews.data.entity.Reddit
import com.manan.test.kotlinnews.domain.utils.Constants
import com.manan.test.kotlinnews.domain.utils.Constants.QUERY_LIMIT
import com.manan.test.kotlinnews.domain.utils.Constants.QUERY_PAGINATE_AFTER
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditAPI {

    @GET(Constants.QUERY_REDDITS)
    fun getPaginatedReddits(
        @Query(QUERY_PAGINATE_AFTER) nextPage: String = "",
        @Query(QUERY_LIMIT) limit: Int = Constants.DEFAULT_LIMIT): Call<Reddit>
}