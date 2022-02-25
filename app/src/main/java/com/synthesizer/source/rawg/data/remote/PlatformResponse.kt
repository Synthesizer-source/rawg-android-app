package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class PlatformResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("slug")
    val slug: String? = null
)