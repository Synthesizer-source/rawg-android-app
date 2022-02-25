package com.synthesizer.source.rawg

import com.google.common.truth.Truth
import com.synthesizer.source.rawg.utils.isNull
import com.synthesizer.source.rawg.utils.orZero
import org.junit.Test

class NumberExtUnitTest {

    @Test
    fun `given null, when called isNull, then return true`(){
        //given
        val number: Number? = null
        val expected = true
        //when
        val actual = number.isNull()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given number, when called isNull, then return false`(){
        //given
        val number: Number = 0.0f
        val expected = false
        //when
        val actual = number.isNull()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given null, when called orZero, then return 0`(){
        //given
        val number: Number? = null
        val expected = 0
        //when
        val actual = number.orZero()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given number, when called orZero, then return number`(){
        //given
        val number: Number = 200.0f
        val expected = 200.0f
        //when
        val actual = number.orZero()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }
}