package com.synthesizer.source.rawg

import com.google.common.truth.Truth
import com.synthesizer.source.rawg.utils.isNull
import com.synthesizer.source.rawg.utils.orZero
import org.junit.Test

class DoubleExtUnitTest {

    @Test
    fun `given null Double, when called isNull, then return true`() {
        //given
        val doubleValue: Double? = null
        val expected = true
        //when
        val actual = doubleValue.isNull()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given non-null Double, when called isNull, then return false`() {
        //given
        val doubleValue = 200.0
        val expected = false
        //when
        val actual = doubleValue.isNull()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given null Double, when called orZero, then return 0_0`() {
        //given
        val doubleValue: Double? = null
        val expected = 0
        //when
        val actual = doubleValue.orZero()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given non-null Double, when called orZero, then return this`() {
        //given
        val doubleValue = 200.0
        val expected = 200.0
        //when
        val actual = doubleValue.orZero()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }
}