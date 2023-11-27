package com.pogrom.kotlinlvl1_2.data.internet

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.converter.gson.GsonConverterFactory


object HttpClient {
    private val apiClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            var request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", "f8ebb86f0amsh3637c8aa46e6674p1a7e43jsna15be98885e8")
                .addHeader("X-RapidAPI-Host", "giphy.p.rapidapi.com")

                .build()
            val url = request.url.newBuilder()
                .addQueryParameter("api_key", "fC02roE2C5fXE9FV6kuEhEv2gY5os2BM")
                .build()
            Log.d("url", url.toString())
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
        .build()

    private val defaultClient = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.giphy.com/v1/gifs/")
        .client(apiClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: Api = retrofit.create<Api>()

    suspend fun downloadGif(url: String): ByteArray{
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        val response = defaultClient.newCall(request).execute()
        if (response.isSuccessful) {
            val body = response.body
            return body?.bytes()!!
        }
        throw Exception()
    }
}