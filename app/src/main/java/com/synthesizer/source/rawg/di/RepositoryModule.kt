package com.synthesizer.source.rawg.di

import com.synthesizer.source.rawg.repository.GameDetailRepository
import com.synthesizer.source.rawg.repository.GameListRepository
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
    fun provideGameDetailRepository() = GameDetailRepository()

    @ViewModelScoped
    @Provides
    fun provideGameListRepository() = GameListRepository()
}