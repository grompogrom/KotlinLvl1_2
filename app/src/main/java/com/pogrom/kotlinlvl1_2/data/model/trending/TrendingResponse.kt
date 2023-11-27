package com.pogrom.kotlinlvl1_2.data.model.trending

data class TrendingResponse(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)