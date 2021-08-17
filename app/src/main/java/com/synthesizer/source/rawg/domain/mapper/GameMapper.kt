package com.synthesizer.source.rawg.domain.mapper

import com.synthesizer.source.rawg.data.remote.GameListItemResponse
import com.synthesizer.source.rawg.domain.model.GameListItem

fun GameListItemResponse.toDomain(): GameListItem {
    return GameListItem(
        id = id,
        name = name,
        imageUrl = background_image!!,
        platforms = parent_platforms!!.map {
            it.platform.slug
        })
}