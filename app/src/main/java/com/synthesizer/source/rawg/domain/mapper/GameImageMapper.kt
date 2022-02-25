package com.synthesizer.source.rawg.domain.mapper

import com.synthesizer.source.rawg.data.remote.ScreenshotResponse
import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.utils.orIntMin

fun ScreenshotResponse.toDomain(): GameImage {
    return GameImage(
        id = id.orIntMin(),
        imageUrl = image.orEmpty()
    )
}