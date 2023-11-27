package com.pogrom.kotlinlvl1_2.data

import com.pogrom.kotlinlvl1_2.data.model.trending.Data

interface ImageRemoteDataSource<T> {
    suspend fun getItems(limit: Int, offset:Int): List<T>
}
