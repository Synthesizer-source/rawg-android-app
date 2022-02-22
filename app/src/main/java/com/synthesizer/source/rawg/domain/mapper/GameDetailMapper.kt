package com.synthesizer.source.rawg.domain.mapper

import com.synthesizer.source.rawg.data.remote.GameDetailResponse
import com.synthesizer.source.rawg.domain.model.GameDetailInfo
import com.synthesizer.source.rawg.utils.convertToDate

fun GameDetailResponse.toDomain(): GameDetailInfo {
    return GameDetailInfo(
        id = id,
        name = name,
        backgroundImage = background_image,
        releaseDate = released.convertToDate(),
        publisher = if (!publishers.isNullOrEmpty()) publishers[0].name else developers[0].name,
        rating = rating.toFloat(),
        metascore = metacritic,
        genres = genres.map { it.name }.sortedBy { it.length },
        description = description_raw,
        platforms = parent_platforms.map { it.platform.slug }
    )
}