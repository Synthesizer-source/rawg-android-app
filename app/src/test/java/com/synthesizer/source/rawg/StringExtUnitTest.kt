package com.synthesizer.source.rawg

import com.google.common.truth.Truth
import com.synthesizer.source.rawg.utils.convertToDate
import org.junit.Test

class StringExtUnitTest {

    @Test
    fun `given empty string, when called convertToDate, then return empty string`() {
        //given
        val given = ""
        val expected = ""
        //when
        val actual = given.convertToDate()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given string has no - delimiter, when called convertToDate, then return empty string`() {
        //given
        val given = "ajdsjkaskjdasjkdkjasdjasjkd"
        val expected = ""
        //when
        val actual = given.convertToDate()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given string has 3 delimeter but year, month, day not int, when called convertToDate, then return empty string`() {
        //given
        val given = "asd-asdasd-asdasd"
        val expected = ""
        //when
        val actual = given.convertToDate()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given date which has wrong year, month or day length, when called convertToDate, then return empty string`() {
        //given
        val given = "1997-1-9"
        val expected = ""
        //when
        val actual = given.convertToDate()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given date which month bigger than 12, when called convertToDate, then return empty string`() {
        //given
        val given = "1997-19-19"
        val expected = ""
        //when
        val actual = given.convertToDate()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `given valid date, when called convertToDate, then return date`() {
        //given
        val given = "1997-12-09"
        val expected = "09 Dec, 1997"
        //when
        val actual = given.convertToDate()
        //then
        Truth.assertThat(actual).isEqualTo(expected)
    }
}