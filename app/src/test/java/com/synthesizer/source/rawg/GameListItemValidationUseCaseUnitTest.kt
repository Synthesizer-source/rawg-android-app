package com.synthesizer.source.rawg

import com.google.common.truth.Truth
import com.synthesizer.source.rawg.core.domain.model.Platforms
import com.synthesizer.source.rawg.data.remote.GameListItemResponse
import com.synthesizer.source.rawg.data.remote.GenreResponse
import com.synthesizer.source.rawg.data.remote.ParentPlatformResponse
import com.synthesizer.source.rawg.data.remote.PlatformResponse
import com.synthesizer.source.rawg.domain.usecase.GameListItemValidationUseCase
import com.synthesizer.source.rawg.domain.usecase.PlatformValidationUseCase
import org.junit.Test

class GameListItemValidationUseCaseUnitTest {

    @Test
    fun `given empty GameListItemResponse, when called invoke, then return false`() {
        //given
        val given = GameListItemValidationUseCase(PlatformValidationUseCase())
        val expected = false
        //when
        val actual = given(GameListItemResponse())
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given response, when called invoke, then return true`() {
        //given
        val given = GameListItemValidationUseCase(PlatformValidationUseCase())
        val response = GameListItemResponse(
            backgroundImage = "something",
            metacritic = 90,
            rating = 4.4,
            released = "something",
            genres = listOf(
                GenreResponse()
            ),
            parentPlatforms = listOf(
                ParentPlatformResponse(PlatformResponse(slug = Platforms.PC)),
                ParentPlatformResponse(PlatformResponse(slug = "something"))
            )
        )
        val expected = true
        //when
        val actual = given(response)
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given response has no valid platforms, when called invoke, then return true`() {
        //given
        val given = GameListItemValidationUseCase(PlatformValidationUseCase())
        val response = GameListItemResponse(
            backgroundImage = "something",
            metacritic = 90,
            rating = 4.4,
            released = "something",
            genres = listOf(
                GenreResponse()
            ),
            parentPlatforms = listOf(
                ParentPlatformResponse(PlatformResponse(slug = "something")),
                ParentPlatformResponse(PlatformResponse(slug = "something"))
            )
        )
        val expected = false
        //when
        val actual = given(response)
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }
}