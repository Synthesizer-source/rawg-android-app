package com.synthesizer.source.rawg.data.remote

data class PlatformWithRequirementsResponse(
    val platform: PlatformWithImageResponse,
    val released_at: String,
    val requirements: RequirementsResponse
)