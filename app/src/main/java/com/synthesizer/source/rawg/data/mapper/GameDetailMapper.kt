package com.synthesizer.source.rawg.data.mapper

import com.synthesizer.source.rawg.data.domain.GameDetailDomain
import com.synthesizer.source.rawg.data.remote.GameDetailRemote
import com.synthesizer.source.rawg.ui.HomeScreenItem

fun GameDetailRemote.toDomain(): GameDetailDomain {
    return GameDetailDomain(
        id = id,
        name = name,
        backgroundImage = background_image,
        releaseDate = released,
        publisher = publishers[0].name,
        rating = rating,
        description = description_raw,
        platforms = parent_platforms.map { it.platform.slug }
    )
}

fun GameDetailRemote.toHomeScreenItem(): HomeScreenItem {
    return HomeScreenItem(id = id, imageUrl = background_image)
}