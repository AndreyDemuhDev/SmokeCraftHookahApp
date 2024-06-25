package com.pidzama.smokecrafthookahapp.di

import com.pidzama.smokecrafthookahapp.data.mapper.RecipeMapper
import com.pidzama.smokecrafthookahapp.data.mapper.RecipeMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MappersModule {


    @Binds
    @Singleton
    abstract fun bindsMappers(mapper: RecipeMapperImpl): RecipeMapper

}