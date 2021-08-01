package com.synthesizer.source.rawg.data.remote

data class Store(
    val id: Int,
    val store: StoreDetail,
    var url: String? = null
)