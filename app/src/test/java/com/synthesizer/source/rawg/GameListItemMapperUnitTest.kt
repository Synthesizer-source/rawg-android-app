package com.synthesizer.source.rawg

import com.google.common.truth.Truth
import com.synthesizer.source.rawg.data.remote.GameListItemResponse
import com.synthesizer.source.rawg.data.remote.ParentPlatformResponse
import com.synthesizer.source.rawg.data.remote.PlatformResponse
import com.synthesizer.source.rawg.domain.mapper.toDomain
import org.junit.Test

class GameListItemMapperUnitTest {

    @Test
    fun `given GameListItemResponse, when called toDomain, then return GameListItem`() {
        //given
        val givenPlatforms = listOf(
            ParentPlatformResponse(PlatformResponse(slug = "platform1")),
            ParentPlatformResponse(PlatformResponse(slug = "platform2")),
            ParentPlatformResponse(PlatformResponse(slug = "platform3")),
            ParentPlatformResponse(PlatformResponse(slug = "platform4")),
            ParentPlatformResponse(PlatformResponse(slug = "platform5"))
        )
        val expectedPlatforms =
            listOf("platform1", "platform2", "platform3", "platform4", "platform5")
        val given = GameListItemResponse(
            id = 1,
            name = "name",
            backgroundImage = "imageUrl",
            parentPlatforms = givenPlatforms
        )
        //when
        val actual = given.toDomain()
        //called
        Truth.assertThat(actual.id).isEqualTo(given.id)
        Truth.assertThat(actual.name).isEqualTo(given.name)
        Truth.assertThat(actual.imageUrl).isEqualTo(given.backgroundImage)
        Truth.assertThat(actual.platforms).isEqualTo(expectedPlatforms)
    }
}