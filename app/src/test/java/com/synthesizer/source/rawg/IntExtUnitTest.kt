package com.synthesizer.source.rawg

import com.google.common.truth.Truth
import com.synthesizer.source.rawg.utils.isNull
import com.synthesizer.source.rawg.utils.isNullOrZero
import com.synthesizer.source.rawg.utils.orIntMin
import com.synthesizer.source.rawg.utils.orZero
import org.junit.Test

class IntExtUnitTest {

    @Test
    fun `given null Int, when called isNull, then return true`() {
        //given
        val intValue: Int? = null
        val expected = true
        //when
        val actual = intValue.isNull()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given non-null Int, when called isNull, then return false`() {
        //given
        val intValue = 200
        val expected = false
        //when
        val actual = intValue.isNull()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given null Int, when called orZero, then return 0`() {
        //given
        val intValue: Int? = null
        val expected = 0
        //when
        val actual = intValue.orZero()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given non-null Int, when called orZero, then return this`() {
        //given
        val intValue = 200
        val expected = 200
        //when
        val actual = intValue.orZero()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given null Int, when called orIntMin, then return Int_MIN_VALUE`() {
        //given
        val intValue: Int? = null
        val expected = Int.MIN_VALUE
        //when
        val actual = intValue.orIntMin()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given non-null Int, when called orIntMin, then return this`() {
        //given
        val intValue = 200
        val expected = 200
        //when
        val actual = intValue.orIntMin()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given null Int, when called isNullOrZero, then return true`() {
        //given
        val intValue: Int? = null
        val expected = true
        //when
        val actual = intValue.isNullOrZero()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given 0, when called isNullOrZero, then return true`() {
        //given
        val intValue = 0
        val expected = true
        //when
        val actual = intValue.isNullOrZero()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given non null and non zero Int, when called isNullOrZero, then return false`() {
        //given
        val intValue = Int.MAX_VALUE
        val expected = false
        //when
        val actual = intValue.isNullOrZero()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }
}