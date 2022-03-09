package com.synthesizer.source.rawg

import com.google.common.truth.Truth
import com.synthesizer.source.rawg.data.remote.GameDetailResponse
import com.synthesizer.source.rawg.data.remote.ScreenshotResponse
import com.synthesizer.source.rawg.domain.mapper.toDomain
import com.synthesizer.source.rawg.domain.mapper.toGameImage
import org.junit.Test

class GameImageMapperUnitTest {

    @Test
    fun `given GameDetailResponse, when called toGameImage, then return GameImage`() {
        //given
        val id = 1
        val backgroundImageUrl = "url"
        val given = GameDetailResponse(id = id, backgroundImage = backgroundImageUrl)
        //when
        val actual = given.toGameImage()
        //then
        Truth.assertThat(actual.id).isEqualTo(given.id)
        Truth.assertThat(actual.imageUrl).isEqualTo(given.backgroundImage)
    }

    @Test
    fun `given ScreenshotResponse, when called toDomain, then return GameImage`() {
        //given
        val id = 1
        val imageUrl = "url"
        val given = ScreenshotResponse(id = id, image = imageUrl)
        //when
        val actual = given.toDomain()
        //then
        Truth.assertThat(actual.id).isEqualTo(given.id)
        Truth.assertThat(actual.imageUrl).isEqualTo(given.image)
    }
}