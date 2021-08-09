package com.synthesizer.source.rawg.data.mapper

import com.synthesizer.source.rawg.data.domain.GameDomain
import com.synthesizer.source.rawg.data.remote.GameRemote

fun GameRemote.toDomain(): GameDomain {
    return GameDomain(
        id = id,
        name = name,
        imageUrl = background_image!!,
        platforms = parent_platforms!!.map {
            it.platform.slug
        })
}