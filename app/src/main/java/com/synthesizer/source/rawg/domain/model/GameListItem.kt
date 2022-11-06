package com.synthesizer.source.rawg.domain.model

import com.synthesizer.source.rawg.common.platform.PlatformUIModel

data class GameListItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val platformUIModel: PlatformUIModel
)