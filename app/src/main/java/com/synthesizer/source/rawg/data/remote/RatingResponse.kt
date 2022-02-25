package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class RatingResponse(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("percent")
    val percent: Double? = null,
    @SerializedName("title")
    val title: String? = null
)