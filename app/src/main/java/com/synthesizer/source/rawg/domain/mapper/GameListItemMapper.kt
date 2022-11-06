package com.synthesizer.source.rawg.domain.mapper

import com.synthesizer.source.rawg.common.platform.PlatformUIModel
import com.synthesizer.source.rawg.data.remote.GameListItemResponse
import com.synthesizer.source.rawg.domain.model.GameListItem
import com.synthesizer.source.rawg.utils.orIntMin

fun GameListItemResponse.toDomain(): GameListItem {
    return GameListItem(
        id = id.orIntMin(),
        name = name.orEmpty(),
        imageUrl = backgroundImage.orEmpty(),
        platformUIModel = PlatformUIModel(
            parentPlatforms.orEmpty().mapNotNull { it.platform?.slug })
    )
}