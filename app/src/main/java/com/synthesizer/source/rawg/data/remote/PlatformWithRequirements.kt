package com.synthesizer.source.rawg.data.remote

data class PlatformWithRequirements(
    val platform: PlatformWithImage,
    val released_at: String,
    val requirements: Requirements
)