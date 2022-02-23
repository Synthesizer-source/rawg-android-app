package com.synthesizer.source.rawg

import com.google.common.truth.Truth
import com.synthesizer.source.rawg.data.Resource
import java.io.IOException
import java.net.SocketTimeoutException
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class ResourceUnitTest {

    @Test
    fun `given response success, when called Resource_of, then return Resource_Success`() {
        //given
        val body = ""
        val code = (200..299).random()
        val response = Response.success(code, body)
        val expected = Resource.Success(body)
        //when
        val actual = Resource.of { response }
        //then
        Truth.assertThat(actual).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat((actual as Resource.Success).data).isEqualTo(expected.data)
    }

    @Test
    fun `given response error, when called Resource_of, then return Resource_Error with HttpException`() {
        //given
        val body = ""
        val code = (400..499).random()
        val response = Response.error<String>(code, body.toByteArray().toResponseBody())
        val expected = Resource.Error(HttpException(response))
        //when
        val actual = Resource.of { response }
        //then
        Truth.assertThat(actual).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat((actual as Resource.Error).throwable)
            .isInstanceOf(expected.throwable.javaClass)
        Truth.assertThat((actual.throwable as HttpException).code()).isEqualTo(code)
        Truth.assertThat(actual.throwable.message).isEqualTo(expected.throwable.message)
    }

    @Test
    fun `given response error, when called Resource_of, then return Resource_Error with IOException`() {
        //given
        val body = ""
        val code = 500
        val response = Response.error<String>(code, body.toByteArray().toResponseBody())
        val expected = Resource.Error(IOException(response.message()))
        //when
        val actual = Resource.of { response }
        //then
        Truth.assertThat(actual).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat((actual as Resource.Error).throwable)
            .isInstanceOf(expected.throwable.javaClass)
        Truth.assertThat(actual.throwable.message).isEqualTo(expected.throwable.message)
    }

    @Test
    fun `given throwable response callback, when called Resource_of, then return Resource_Error with SocketTimeoutException`() {
        //given
        val throwable = SocketTimeoutException()
        val expected = Resource.Error(throwable)
        val f: () -> Response<Any> = {
            throw throwable
        }
        //when
        val actual = Resource.of { f() }
        //then
        Truth.assertThat(actual).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat((actual as Resource.Error).throwable)
            .isInstanceOf(expected.throwable.javaClass)
        Truth.assertThat(actual.throwable.message)
            .isEqualTo(expected.throwable.message)
    }
}