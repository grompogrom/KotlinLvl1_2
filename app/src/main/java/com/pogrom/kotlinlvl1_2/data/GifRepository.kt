package com.pogrom.kotlinlvl1_2.data

import com.pogrom.kotlinlvl1_2.data.model.ImageItem
import com.pogrom.kotlinlvl1_2.data.model.trending.Data
import java.net.ConnectException

class GifRepository(
    private val imageRemoteDataSource: ImageRemoteDataSource<Data>,
    private val imageLocalDataSource: ImageLocalDataSource<ImageItem>
    ) {

    suspend fun getItems(page: Int, pageSize: Int): Result<List<ImageItem>>{
        try {
            val imageItems =  try {
                loadRemoteItems(page,pageSize)
            } catch (e: ConnectException){
                loadLocalItems(page, pageSize)
            }
            return Result.success(imageItems)
        }
        catch (e: Throwable) {
            return Result.failure(e)
        }
    }

    private suspend fun loadLocalItems(page: Int, pageSize: Int): List<ImageItem> {
        val cacheItems = imageLocalDataSource.getItems()
        if ( cacheItems.isNullOrEmpty() || page * pageSize >= cacheItems.size) {
            throw Throwable("No more data")
        }
        return cacheItems
    }

    private suspend fun loadRemoteItems(page: Int, pageSize: Int): List<ImageItem> {
        val imagesData = imageRemoteDataSource.getItems(pageSize, page * pageSize)
        val imageItems = imagesData.map {
            ImageItem.fromTrendingResponse(it)
        }
        imageLocalDataSource.putItems(imageItems)
        return imageItems
    }

}