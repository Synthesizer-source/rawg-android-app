package com.synthesizer.source.rawg.domain.mapper

import com.synthesizer.source.rawg.data.remote.GameDetailResponse
import com.synthesizer.source.rawg.domain.model.GameDetail

fun GameDetailResponse.toDomain(): GameDetail {
    return GameDetail(
        id = id,
        name = name,
        backgroundImage = background_image,
        releaseDate = released,
        publisher = if (!publishers.isNullOrEmpty()) publishers[0].name else developers[0].name,
        rating = rating,
        metascore = metacritic,
        genres = genres.map { it.name }.sortedBy { it.length },
        description = description_raw,
        platforms = parent_platforms.map { it.platform.slug }
    )
}