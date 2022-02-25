package com.synthesizer.source.rawg.domain.mapper

import com.synthesizer.source.rawg.data.remote.GameDetailResponse
import com.synthesizer.source.rawg.domain.model.GameDetailInfo
import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.utils.convertToDate
import com.synthesizer.source.rawg.utils.orIntMin
import com.synthesizer.source.rawg.utils.orZero

fun GameDetailResponse.toDomain(): GameDetailInfo {
    return GameDetailInfo(
        id = id.orIntMin(),
        name = name.orEmpty(),
        backgroundImage = backgroundImage.orEmpty(),
        releaseDate = released.orEmpty().convertToDate(),
        publisher = (if (!publishers.isNullOrEmpty()) publishers[0].name else developers.orEmpty()
            .getOrNull(0)?.name).orEmpty(),
        rating = rating.orZero().toFloat(),
        metascore = metacritic.orZero(),
        genres = genres.orEmpty().mapNotNull { it.name }.sortedBy { it.length },
        description = descriptionRaw.orEmpty(),
        platforms = parentPlatforms.orEmpty().mapNotNull { it.platform }.mapNotNull { it.slug }
    )
}

fun GameDetailResponse.getGameImage(): GameImage {
    return GameImage(id.orIntMin(), backgroundImage.orEmpty())
}