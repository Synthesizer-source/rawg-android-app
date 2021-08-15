package com.synthesizer.source.rawg.data.remote

data class GameListResponse(
    val count: Int,
    val description: String,
    val filters: FiltersResponse,
    val next: String,
    val nofollow: Boolean,
    val nofollow_collections: List<String>,
    val noindex: Boolean,
    val previous: Any,
    val results: List<GameListItemResponse>,
    val seo_description: String,
    val seo_h1: String,
    val seo_keywords: String,
    val seo_title: String
)