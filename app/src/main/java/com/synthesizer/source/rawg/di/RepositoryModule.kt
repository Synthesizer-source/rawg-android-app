package com.synthesizer.source.rawg.di

import com.synthesizer.source.rawg.data.api.RawgService
import com.synthesizer.source.rawg.data.repository.GameDetailRepository
import com.synthesizer.source.rawg.data.repository.GameListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideGameDetailRepository(service: RawgService) = GameDetailRepository(service)

    @ViewModelScoped
    @Provides
    fun provideGameListRepository(service: RawgService) = GameListRepository(service)
}