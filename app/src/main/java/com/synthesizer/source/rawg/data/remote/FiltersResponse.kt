package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class FiltersResponse(
    @SerializedName("years")
    val yearDetails: List<YearDetailResponse>? = null
)