package com.synthesizer.source.rawg.data.remote

import com.google.gson.annotations.SerializedName

class PlatformWithImageResponse(
    @SerializedName("games_count")
    val gamesCount: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: Any? = null,
    @SerializedName("image_background")
    val imageBackground: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("slug")
    val slug: String? = null,
    @SerializedName("year_end")
    val yearEnd: Any? = null,
    @SerializedName("year_start")
    val yearStart: Int? = null
)