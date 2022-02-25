package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class StoreResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("store")
    val store: StoreDetailResponse? = null,
    @SerializedName("url")
    val url: String? = null
)