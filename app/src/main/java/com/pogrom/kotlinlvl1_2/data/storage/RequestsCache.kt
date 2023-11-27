package com.pogrom.kotlinlvl1_2.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pogrom.kotlinlvl1_2.data.ImageLocalDataSource
import com.pogrom.kotlinlvl1_2.data.model.ImageItem
import com.pogrom.kotlinlvl1_2.getValueByKey
import com.pogrom.kotlinlvl1_2.readAllKeys

class RequestsCache(
    private val dataStore: DataStore<Preferences>
): ImageLocalDataSource<ImageItem> {

    override suspend fun getItems(): List<ImageItem>? {
        val keys = dataStore.readAllKeys()?.filter { it.name.startsWith("ImageItem_") }
        val strItems = keys
            ?.map { dataStore.getValueByKey(it) as String }
        return strItems?.map { ImageItem.fromString(it) }
    }

    override suspend fun putItems(items: List<ImageItem>) {
        clearCache()
        dataStore.edit { images ->
            items.forEach {item ->
                val key = stringPreferencesKey("ImageItem_${item.id}")
                images[key] = item.toString()
            }
        }
    }

    private suspend fun clearCache(){
        val keys = dataStore.readAllKeys()?.filter { it.name.startsWith("ImageItem_") }
        dataStore.edit { images ->
            keys?.forEach {key ->
                images -= key
            }
        }
    }

    suspend fun getSize(): Int {
        val keys = dataStore.readAllKeys()
        return keys?.count {it.name.startsWith("ImageItem_")  } ?: 0
    }
}