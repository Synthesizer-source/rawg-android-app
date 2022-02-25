package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class GameListResponse(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("filters")
    val filters: FiltersResponse? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("nofollow")
    val nofollow: Boolean? = null,
    @SerializedName("nofollow_collections")
    val nofollowCollections: List<String>? = null,
    @SerializedName("noindex")
    val noindex: Boolean? = null,
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("results")
    val results: List<GameListItemResponse>? = null,
    @SerializedName("seo_description")
    val seoDescription: String? = null,
    @SerializedName("seo_h1")
    val seoH1: String? = null,
    @SerializedName("seo_keywords")
    val seoKeywords: String? = null,
    @SerializedName("seo_title")
    val seoTitle: String? = null
)