package com.synthesizer.source.rawg.domain.usecase

import com.synthesizer.source.rawg.data.Resource
import com.synthesizer.source.rawg.domain.model.GameDetailWithScreenshots
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class FetchGameDetailWithScreenshotsUseCase @Inject constructor(
    private val fetchGameDetailUseCase: FetchGameDetailUseCase,
    private val fetchGameScreenshotsUseCase: FetchGameScreenshotsUseCase
) {
    operator fun invoke(id: Int) = flow<Resource<GameDetailWithScreenshots>> {
        combine(
            fetchGameDetailUseCase(id),
            fetchGameScreenshotsUseCase(id)
        ) { detailResource, screenshotsResource ->
            if (detailResource is Resource.Error) throw detailResource.throwable
            if (screenshotsResource is Resource.Error) throw screenshotsResource.throwable
            val detail = (detailResource as Resource.Success).data
            val screenshots = (screenshotsResource as Resource.Success).data
            GameDetailWithScreenshots(detail, screenshots)
        }.onStart { emit(Resource.Loading()) }
            .catch { emit(Resource.Error(it)) }
            .collect {
                emit(Resource.Success(it))
            }
    }
}