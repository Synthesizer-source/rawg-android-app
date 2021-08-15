package com.synthesizer.source.rawg.data.remote

data class GameListItemResponse(
    val added: Int,
    val added_by_status: AddedByStatusResponse,
    val background_image: String?,
    val clip: Any,
    val dominant_color: String,
    val esrb_rating: EsrbRatingResponse,
    val genres: List<GenreResponse>,
    val id: Int,
    val metacritic: Int?,
    val name: String,
    val parent_platforms: List<ParentPlatformResponse>?,
    val platforms: List<PlatformMultipleLanguageResponse>,
    val playtime: Int,
    val rating: Double?,
    val rating_top: Int,
    val ratings: List<RatingResponse>,
    val ratings_count: Int,
    val released: String?,
    val reviews_count: Int,
    val reviews_text_count: Int,
    val saturated_color: String,
    val short_screenshots: List<ShortScreenshotResponse>,
    val slug: String,
    val stores: List<StoreResponse>,
    val suggestions_count: Int,
    val tags: List<TagResponse>,
    val tba: Boolean,
    val updated: String,
    val user_game: Any
) {
    fun isValid(): Boolean {
        return background_image != null &&
                metacritic != null &&
                metacritic != 0 &&
                parent_platforms != null &&
                (parent_platforms.count {
                    it.platform.slug == "pc" ||
                            it.platform.slug == "playstation" ||
                            it.platform.slug == "xbox" ||
                            it.platform.slug == "nintendo"
                } > 0) &&
                rating != null &&
                released != null &&
                genres.isNotEmpty()
    }
}