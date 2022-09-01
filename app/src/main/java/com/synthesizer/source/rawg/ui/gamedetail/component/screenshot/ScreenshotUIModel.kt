package com.synthesizer.source.rawg.ui.gamedetail.component.screenshot

import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.ui.gamedetail.component.Component

class ScreenshotUIModel(val screenshots: List<GameImage>) : Component {
    override val type: String
        get() = "Screenshot"
}