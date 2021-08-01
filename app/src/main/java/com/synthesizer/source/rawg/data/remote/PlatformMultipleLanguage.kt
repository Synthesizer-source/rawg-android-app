package com.synthesizer.source.rawg.data.remote

data class PlatformMultipleLanguage(
    val platform: PlatformWithImage,
    val released_at: String,
    val requirements_en: Any,
    val requirements_ru: Any
)