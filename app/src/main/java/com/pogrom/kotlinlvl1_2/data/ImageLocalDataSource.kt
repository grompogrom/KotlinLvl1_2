package com.pogrom.kotlinlvl1_2.data


interface ImageLocalDataSource<T> {
    suspend fun getItems(): List<T>?
    suspend fun putItems(items: List<T>)
}
