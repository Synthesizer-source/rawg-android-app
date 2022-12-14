package com.synthesizer.source.rawg.ui.gamedetail.component.description

import com.synthesizer.source.rawg.ui.gamedetail.component.Component
import com.synthesizer.source.rawg.ui.gamedetail.component.ViewType

class DescriptionUIModel(val description: String) : Component {
    override val type: ViewType
        get() = ViewType.DESCRIPTION_VIEW_TYPE
}