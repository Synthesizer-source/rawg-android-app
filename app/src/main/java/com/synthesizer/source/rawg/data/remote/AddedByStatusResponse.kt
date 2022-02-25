package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class AddedByStatusResponse(
    @SerializedName("beaten")
    val beaten: Int? = null,
    @SerializedName("dropped")
    val dropped: Int? = null,
    @SerializedName("owned")
    val owned: Int? = null,
    @SerializedName("playing")
    val playing: Int? = null,
    @SerializedName("toplay")
    val toplay: Int? = null,
    @SerializedName("yet")
    val yet: Int? = null
)