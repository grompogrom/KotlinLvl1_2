package com.pogrom.kotlinlvl1_2.ui

import com.pogrom.kotlinlvl1_2.data.model.ImageItem

data class ScreenState(
    val isLoading: Boolean = false,
    val items: List<ImageItem> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)
