package com.pogrom.kotlinlvl1_2.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pogrom.kotlinlvl1_2.data.GifRepository
import com.pogrom.kotlinlvl1_2.data.PaginatorImpl
import com.pogrom.kotlinlvl1_2.data.internet.ApiDataSource
import com.pogrom.kotlinlvl1_2.data.storage.RequestsCache
import com.pogrom.kotlinlvl1_2.dataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: GifRepository
): ViewModel() {
    var state by mutableStateOf(ScreenState())
    private val paginator = PaginatorImpl(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            repository.getItems(nextPage, 20)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            Log.d("viewModel", "loading data error")
            state = state.copy(error=it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                    items =state.items + items,
                    page = newKey,
                    endReached = items.isEmpty()
                )

        }
    )
    init {
        loadNextItems()
    }
    fun loadNextItems(){
        state = state.copy(error = null)
        viewModelScope.launch {
            delay(600)
            paginator.loadItems()
        }
    }

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel(
                    repository = GifRepository(
                        imageLocalDataSource = RequestsCache(context.dataStore),
                        imageRemoteDataSource = ApiDataSource()
                    )
                )
            }
        }
    }


    }