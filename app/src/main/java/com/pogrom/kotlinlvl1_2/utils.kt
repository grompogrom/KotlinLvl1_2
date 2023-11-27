package com.pogrom.kotlinlvl1_2

import coil.ImageLoader
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cache")

suspend fun DataStore<Preferences>.readAllKeys(): Set<Preferences.Key<*>>? {
    val keys = this.data
        .map {
            it.asMap().keys
        }
    return keys.firstOrNull()
}

suspend fun DataStore<Preferences>.getValueByKey(key: Preferences.Key<*>): Any? {
    val value = this.data
        .map {
            it[key]
        }
    return value.firstOrNull()
}