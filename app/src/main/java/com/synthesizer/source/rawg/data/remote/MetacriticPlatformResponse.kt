package com.synthesizer.source.rawg.data.remote

data class MetacriticPlatformResponse(
    val metascore: Int,
    val platform: PlatformResponse,
    val url: String
)