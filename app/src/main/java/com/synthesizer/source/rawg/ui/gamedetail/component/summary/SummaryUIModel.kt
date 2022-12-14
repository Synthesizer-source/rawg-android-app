package com.synthesizer.source.rawg.ui.gamedetail.component.summary

import com.synthesizer.source.rawg.ui.gamedetail.component.Component
import com.synthesizer.source.rawg.ui.gamedetail.component.ViewType

class SummaryUIModel(
    val gameName: String,
    val releaseDate: String,
    val publisherName: String,
    val rating: Float,
    val platforms: List<String>,
    val metascore: Int,
    val genres: List<String>
) : Component {
    override val type: ViewType
        get() = ViewType.SUMMARY_VIEW_TYPE
}