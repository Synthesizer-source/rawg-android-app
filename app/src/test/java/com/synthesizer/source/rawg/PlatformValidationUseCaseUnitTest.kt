package com.synthesizer.source.rawg

import com.google.common.truth.Truth
import com.synthesizer.source.rawg.data.remote.PlatformResponse
import com.synthesizer.source.rawg.domain.usecase.PlatformValidationUseCase
import org.junit.Test

class PlatformValidationUseCaseUnitTest {

    @Test
    fun `given pc, when called platformValidationUseCase, then return true`() {
        //given
        val platform = PlatformResponse(slug = "pc")
        val given = PlatformValidationUseCase()
        val expected = true
        //when
        val actual = given(platform)
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given playstation, when called platformValidationUseCase, then return true`() {
        //given
        val platform = PlatformResponse(slug = "playstation")
        val given = PlatformValidationUseCase()
        val expected = true
        //when
        val actual = given(platform)
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given xbox, when called platformValidationUseCase, then return true`() {
        //given
        val platform = PlatformResponse(slug = "xbox")
        val given = PlatformValidationUseCase()
        val expected = true
        //when
        val actual = given(platform)
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given nintendo, when called platformValidationUseCase, then return true`() {
        //given
        val platform = PlatformResponse(slug = "nintendo")
        val given = PlatformValidationUseCase()
        val expected = true
        //when
        val actual = given(platform)
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given something, when called platformValidationUseCase, then return false`() {
        //given
        val platform = PlatformResponse(slug = "something")
        val given = PlatformValidationUseCase()
        val expected = false
        //when
        val actual = given(platform)
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }
}