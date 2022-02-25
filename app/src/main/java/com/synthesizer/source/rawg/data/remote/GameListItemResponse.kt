package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class GameListItemResponse(
    @SerializedName("added")
    val added: Int? = null,
    @SerializedName("added_by_status")
    val addedByStatus: AddedByStatusResponse? = null,
    @SerializedName("background_image")
    val backgroundImage: String? = null,
    @SerializedName("clip")
    val clip: Any? = null,
    @SerializedName("dominant_color")
    val dominantColor: String? = null,
    @SerializedName("esrb_rating")
    val esrbRating: EsrbRatingResponse? = null,
    @SerializedName("genres")
    val genres: List<GenreResponse>? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("metacritic")
    val metacritic: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("parent_platforms")
    val parentPlatforms: List<ParentPlatformResponse>? = null,
    @SerializedName("platforms")
    val platforms: List<PlatformMultipleLanguageResponse>? = null,
    @SerializedName("playtime")
    val playtime: Int? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("rating_top")
    val ratingTop: Int? = null,
    @SerializedName("ratings")
    val ratings: List<RatingResponse>? = null,
    @SerializedName("ratings_count")
    val ratingsCount: Int? = null,
    @SerializedName("released")
    val released: String? = null,
    @SerializedName("reviews_count")
    val reviewsCount: Int? = null,
    @SerializedName("reviews_text_count")
    val reviewsTextCount: Int? = null,
    @SerializedName("saturated_color")
    val saturatedColor: String? = null,
    @SerializedName("short_screenshots")
    val shortScreenshots: List<ShortScreenshotResponse>? = null,
    @SerializedName("slug")
    val slug: String? = null,
    @SerializedName("stores")
    val stores: List<StoreResponse>? = null,
    @SerializedName("suggestions_count")
    val suggestionsCount: Int? = null,
    @SerializedName("tags")
    val tags: List<TagResponse>? = null,
    @SerializedName("tba")
    val tba: Boolean? = null,
    @SerializedName("updated")
    val updated: String? = null,
    @SerializedName("user_game")
    val userGame: Any? = null
)