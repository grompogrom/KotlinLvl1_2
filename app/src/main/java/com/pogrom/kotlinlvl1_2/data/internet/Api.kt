package com.pogrom.kotlinlvl1_2.data.internet

import android.graphics.Bitmap
import com.pogrom.kotlinlvl1_2.data.model.trending.TrendingResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface Api {

    @GET("trending")
    suspend fun getImagesList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String = "PG-13",
        @Query("bundle") bundle: String = "messaging_non_clips"
    ): TrendingResponse

    @GET
    suspend fun downloadGif(@Url fileURl: String): ByteArray

}
