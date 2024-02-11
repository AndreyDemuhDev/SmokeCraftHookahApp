package com.pidzama.smokecrafthookahapp.di

import com.pidzama.smokecrafthookahapp.data.repository.RecipeRepository
import com.pidzama.smokecrafthookahapp.data.repository.RecipeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(
        repo: RecipeRepositoryImpl
    ): RecipeRepository
}