package com.synthesizer.source.rawg.data.mapper

import com.synthesizer.source.rawg.data.domain.GameDetailDomain
import com.synthesizer.source.rawg.data.domain.HomeGameItem
import com.synthesizer.source.rawg.data.remote.GameDetailRemote

fun GameDetailRemote.toDomain(): GameDetailDomain {
    return GameDetailDomain(
        id = id,
        name = name,
        backgroundImage = background_image,
        releaseDate = released,
        publisher = if (!publishers.isNullOrEmpty()) publishers[0].name else developers[0].name,
        rating = rating,
        description = description_raw,
        platforms = parent_platforms.map { it.platform.slug }
    )
}

fun GameDetailRemote.toHomeScreenItem(): HomeGameItem {
    return HomeGameItem(id = id, imageUrl = background_image)
}