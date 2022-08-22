package com.synthesizer.source.rawg.ui.gamedetail.component.summary

class SummaryUIModel(
    val gameName: String,
    val releaseDate: String,
    val publisherName: String,
    val rating: Float,
    val platforms: List<String>,
    val metascore: Int,
    val genres: List<String>
)