package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class MetacriticPlatformResponse(
    @SerializedName("metascore")
    val metascore: Int? = null,
    @SerializedName("platform")
    val platform: PlatformResponse? = null,
    @SerializedName("url")
    val url: String? = null
)