package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class RequirementsResponse(
    @SerializedName("minimum")
    val minimum: String? = null,
    @SerializedName("recommended")
    val recommended: String? = null
)