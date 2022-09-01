package com.synthesizer.source.rawg.ui.gamedetail.component.description

import com.synthesizer.source.rawg.ui.gamedetail.component.Component

class DescriptionUIModel(val description: String) : Component {
    override val type: String
        get() = "Description"
}