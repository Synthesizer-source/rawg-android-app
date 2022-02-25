package com.synthesizer.source.rawg

import com.google.common.truth.Truth
import com.synthesizer.source.rawg.data.remote.DeveloperResponse
import com.synthesizer.source.rawg.data.remote.GameDetailResponse
import com.synthesizer.source.rawg.data.remote.GenreResponse
import com.synthesizer.source.rawg.data.remote.PublisherResponse
import com.synthesizer.source.rawg.domain.mapper.toDomain
import org.junit.Test

class GameDetailMapperTest {

    @Test
    fun `given null publishers and null developers, when called toDomain, then return publisher is empty string`() {
        //given
        val given = GameDetailResponse()
        val expected = ""
        //when
        val actual = given.toDomain()
        //then
        Truth.assertThat(actual.publisher).isEqualTo(expected)
    }

    @Test
    fun `given null publishers and developers, when called toDomain, then return publisher is first developer`() {
        //given
        val developers = listOf(
            DeveloperResponse(name = "developerOne"),
            DeveloperResponse(name = "developerTwo"),
            DeveloperResponse(name = "developerX")
        )
        val given = GameDetailResponse(developers = developers)
        val expected = "developerOne"
        //when
        val actual = given.toDomain()
        //then
        Truth.assertThat(actual.publisher).isEqualTo(expected)
    }

    @Test
    fun `given publishers and developers, when called toDomain, then return publisher is first publisher`() {
        //given
        val publishers = listOf(
            PublisherResponse(name = "publisherOne"),
            PublisherResponse(name = "randomPublisher"),
            PublisherResponse(name = "anotherPublisher")
        )
        val developers = listOf(
            DeveloperResponse(name = "developerOne"),
            DeveloperResponse(name = "developerTwo"),
            DeveloperResponse(name = "developerX")
        )
        val given = GameDetailResponse(developers = developers, publishers = publishers)
        val expected = "publisherOne"
        //when
        val actual = given.toDomain()
        //then
        Truth.assertThat(actual.publisher).isEqualTo(expected)
    }

    @Test
    fun `given genres, when called toDomain, then return ordered genres by name length`() {
        //given
        val genres = listOf(
            GenreResponse(name = "Action"),
            GenreResponse(name = "Adventure"),
            GenreResponse(name = "FPS"),
            GenreResponse(name = "RPG")
        )
        val given = GameDetailResponse(genres = genres)
        val expected = listOf("FPS", "RPG", "Action", "Adventure")
        //when
        val actual = given.toDomain()
        //then
        Truth.assertThat(actual.genres).isEqualTo(expected)
    }
}