package com.synthesizer.source.rawg.data.mapper

import com.synthesizer.source.rawg.data.domain.GameListItem
import com.synthesizer.source.rawg.data.remote.GameListItemResponse

fun GameListItemResponse.toDomain(): GameListItem {
    return GameListItem(
        id = id,
        name = name,
        imageUrl = background_image!!,
        platforms = parent_platforms!!.map {
            it.platform.slug
        })
}