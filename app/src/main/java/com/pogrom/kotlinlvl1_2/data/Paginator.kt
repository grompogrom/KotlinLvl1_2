package com.pogrom.kotlinlvl1_2.data

interface Paginator<Key, Item> {

    suspend fun loadItems()
    suspend fun reset()
}