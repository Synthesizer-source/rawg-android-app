package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class GameScreenshotResponse(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("results")
    val results: List<ScreenshotResponse>? = null
)