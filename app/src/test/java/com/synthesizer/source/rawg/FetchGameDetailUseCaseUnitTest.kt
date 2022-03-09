package com.synthesizer.source.rawg

import com.google.common.truth.Truth
import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.data.remote.GameDetailResponse
import com.synthesizer.source.rawg.data.repository.GameDetailRepository
import com.synthesizer.source.rawg.domain.mapper.toDomain
import com.synthesizer.source.rawg.domain.usecase.FetchGameDetailUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class FetchGameDetailUseCaseUnitTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @RelaxedMockK
    lateinit var repository: GameDetailRepository

    init {
        MockKAnnotations.init(this)
    }

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun reset() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `given success response, when called invoke, then return success resource`() =
        testCoroutineDispatcher.runBlockingTest {
            //given
            val id = 1
            val response = GameDetailResponse()
            val expected = Resource.Success(response.toDomain())
            coEvery { repository.fetchGameDetail(id) }.returns(Response.success(response))
            val given = FetchGameDetailUseCase(repository)
            //when
            val actual = given(id).single()
            //then
            Truth.assertThat(actual).isInstanceOf(Resource.Success::class.java)
            Truth.assertThat((actual as Resource.Success).data).isEqualTo(expected.data)
        }

    @Test
    fun `given error response, when called invoke, then return success resource`() =
        testCoroutineDispatcher.runBlockingTest {
            //given
            val id = 1
            val errorCode = 400
            val errorResponse = "".toResponseBody()
            val expected = Resource.Error(mockk<HttpException>())
            coEvery { repository.fetchGameDetail(id) }.returns(
                Response.error(
                    errorCode,
                    errorResponse
                )
            )
            val given = FetchGameDetailUseCase(repository)
            //when
            val actual = given(id).single()
            //then
            Truth.assertThat(actual).isInstanceOf(Resource.Error::class.java)
            Truth.assertThat((actual as Resource.Error).throwable as HttpException)
                .isInstanceOf(HttpException::class.java)
            Truth.assertThat(actual.throwable).isInstanceOf(expected.throwable::class.java)
        }
}