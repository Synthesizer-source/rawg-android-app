package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class YearResponse(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("nofollow")
    val nofollow: Boolean? = null,
    @SerializedName("year")
    val year: Int? = null
)