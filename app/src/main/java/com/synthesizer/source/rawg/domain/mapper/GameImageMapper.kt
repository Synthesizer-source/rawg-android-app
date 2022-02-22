package com.synthesizer.source.rawg.domain.mapper

import com.synthesizer.source.rawg.data.remote.ScreenshotResponse
import com.synthesizer.source.rawg.domain.model.GameImage

fun ScreenshotResponse.toDomain(): GameImage {
    return GameImage(
        id = id,
        imageUrl = image
    )
}