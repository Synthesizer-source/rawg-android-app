package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class GameDetailResponse(
    @SerializedName("achievements_count")
    val achievementsCount: Int? = null,
    @SerializedName("added")
    val added: Int? = null,
    @SerializedName("added_by_status")
    val addedByStatus: AddedByStatusResponse? = null,
    @SerializedName("additions_count")
    val additionsCount: Int? = null,
    @SerializedName("alternative_names")
    val alternativeNames: List<String>? = null,
    @SerializedName("background_image")
    val backgroundImage: String? = null,
    @SerializedName("background_image_additional")
    val backgroundImageAdditional: String? = null,
    @SerializedName("clip")
    val clip: Any? = null,
    @SerializedName("creators_count")
    val creatorsCount: Int? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("description_raw")
    val descriptionRaw: String? = null,
    @SerializedName("developers")
    val developers: List<DeveloperResponse>? = null,
    @SerializedName("dominant_color")
    val dominantColor: String? = null,
    @SerializedName("esrb_rating")
    val esrbRating: EsrbRatingResponse? = null,
    @SerializedName("game_series_count")
    val gameSeriesCount: Int? = null,
    @SerializedName("genres")
    val genres: List<GenreResponse>? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("metacritic")
    val metacritic: Int? = null,
    @SerializedName("metacritic_platforms")
    val metacriticPlatforms: List<MetacriticPlatformResponse>? = null,
    @SerializedName("metacritic_url")
    val metacriticUrl: String? = null,
    @SerializedName("movies_count")
    val moviesCount: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("name_original")
    val nameOriginal: String? = null,
    @SerializedName("parent_achievements_count")
    val parentAchievementsCount: Int? = null,
    @SerializedName("parent_platforms")
    val parentPlatforms: List<ParentPlatformResponse>? = null,
    @SerializedName("parents_count")
    val parentsCount: Int? = null,
    @SerializedName("platforms")
    val platforms: List<PlatformWithRequirementsResponse>? = null,
    @SerializedName("playtime")
    val playtime: Int? = null,
    @SerializedName("publishers")
    val publishers: List<PublisherResponse>? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("rating_top")
    val ratingTop: Int? = null,
    @SerializedName("ratings")
    val ratings: List<RatingResponse>? = null,
    @SerializedName("ratings_count")
    val ratingsCount: Int? = null,
    @SerializedName("reactions")
    val reactions: ReactionsResponse? = null,
    @SerializedName("reddit_count")
    val redditCount: Int? = null,
    @SerializedName("reddit_description")
    val redditDescription: String? = null,
    @SerializedName("reddit_logo")
    val redditLogo: String? = null,
    @SerializedName("reddit_name")
    val redditName: String? = null,
    @SerializedName("reddit_url")
    val redditUrl: String? = null,
    @SerializedName("released")
    val released: String? = null,
    @SerializedName("reviews_count")
    val reviewsCount: Int? = null,
    @SerializedName("reviews_text_count")
    val reviewsTextCount: Int? = null,
    @SerializedName("saturated_color")
    val saturatedColor: String? = null,
    @SerializedName("screenshots_count")
    val screenshotsCount: Int? = null,
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
    @SerializedName("twitch_count")
    val twitchCount: Int? = null,
    @SerializedName("updated")
    val updated: String? = null,
    @SerializedName("user_game")
    val userGame: Any? = null,
    @SerializedName("website")
    val website: String? = null,
    @SerializedName("youtube_count")
    val youtubeCount: Int? = null
)