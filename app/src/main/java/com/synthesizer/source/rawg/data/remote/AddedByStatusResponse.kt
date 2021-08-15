package com.synthesizer.source.rawg.data.remote

data class AddedByStatusResponse(
    val beaten: Int,
    val dropped: Int,
    val owned: Int,
    val playing: Int,
    val toplay: Int,
    val yet: Int
)