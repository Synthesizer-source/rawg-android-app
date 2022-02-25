package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class ScreenshotResponse(
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("is_deleted")
    val isDeleted: Boolean? = null,
    @SerializedName("width")
    val width: Int? = null
)