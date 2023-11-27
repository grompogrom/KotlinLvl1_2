package com.pogrom.kotlinlvl1_2.data.model

import com.pogrom.kotlinlvl1_2.data.model.trending.Data

data class ImageItem(
    val id: String,
    val title: String,
    val data: String,
    val aspectRatio: Float
) {
    override fun toString(): String {
        return "$id,$title,$data,$aspectRatio"
    }

    companion object{
        fun fromString(imageItemString: String): ImageItem {
            val parts = imageItemString.split(",")
            return ImageItem(parts[0], parts[1], parts[2], parts[3].toFloat())
        }

        fun fromTrendingResponse(trendingData: Data) = ImageItem(
            id = trendingData.id,
            title = trendingData.title,
            data = trendingData.images.original.url,
            aspectRatio = with(trendingData.images.original){
                width.toFloat() / height.toFloat()
            }
        )
    }

}
