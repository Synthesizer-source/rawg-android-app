package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class ParentPlatformResponse(
    @SerializedName("platform")
    val platform: PlatformResponse? = null
)