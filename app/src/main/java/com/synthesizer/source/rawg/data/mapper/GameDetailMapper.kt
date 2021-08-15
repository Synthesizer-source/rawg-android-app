package com.synthesizer.source.rawg.data.mapper

import com.synthesizer.source.rawg.data.domain.GameDetail
import com.synthesizer.source.rawg.data.domain.HomeGameItem
import com.synthesizer.source.rawg.data.remote.GameDetailResponse

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

fun GameDetailResponse.toHomeScreenItem(): HomeGameItem {
    return HomeGameItem(id = id, imageUrl = background_image)
}