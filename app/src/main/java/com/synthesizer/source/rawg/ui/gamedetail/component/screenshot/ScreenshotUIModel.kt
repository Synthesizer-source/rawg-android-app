package com.synthesizer.source.rawg.ui.gamedetail.component.screenshot

import com.synthesizer.source.rawg.domain.model.GameImage
import com.synthesizer.source.rawg.ui.gamedetail.component.Component
import com.synthesizer.source.rawg.ui.gamedetail.component.ViewType

class ScreenshotUIModel(val screenshots: List<GameImage>) : Component {
    override val type: ViewType
        get() = ViewType.SCREENSHOT_VIEW_TYPE

}