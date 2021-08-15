package com.synthesizer.source.rawg.data.remote

data class StoreResponse(
    val id: Int,
    val store: StoreDetailResponse,
    var url: String? = null
)