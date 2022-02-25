package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class PlatformWithRequirementsResponse(
    @SerializedName("platform")
    val platform: PlatformWithImageResponse? = null,
    @SerializedName("released_at")
    val releasedAt: String? = null,
    @SerializedName("requirements")
    val requirements: RequirementsResponse? = null
)