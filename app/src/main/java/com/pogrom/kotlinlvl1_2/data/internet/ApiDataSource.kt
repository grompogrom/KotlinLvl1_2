package com.pogrom.kotlinlvl1_2.data.internet

import com.pogrom.kotlinlvl1_2.data.ImageRemoteDataSource
import com.pogrom.kotlinlvl1_2.data.model.trending.Data
import java.net.ConnectException

class ApiDataSource: ImageRemoteDataSource<Data> {
    override suspend fun getItems(limit: Int, offset:Int): List<Data>{
        return try {
            HttpClient.api.getImagesList(limit, offset).data
        } catch (e: Throwable) {
            throw ConnectException()
        }
    }
}