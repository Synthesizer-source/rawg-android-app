package com.synthesizer.source.rawg.data.remote

data class YearDetail(
    val count: Int,
    val decade: Int,
    val filter: String,
    val from: Int,
    val nofollow: Boolean,
    val to: Int,
    val years: List<Year>
)