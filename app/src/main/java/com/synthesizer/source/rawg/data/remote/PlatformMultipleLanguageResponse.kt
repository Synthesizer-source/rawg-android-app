package com.synthesizer.source.rawg.data.remote

data class PlatformMultipleLanguageResponse(
    val platform: PlatformWithImageResponse,
    val released_at: String,
    val requirements_en: Any,
    val requirements_ru: Any
)