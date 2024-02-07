package com.pidzama.smokecrafthookahapp.di


import com.pidzama.smokecrafthookahapp.data.DataStoreRepository
import com.pidzama.smokecrafthookahapp.data.network.SmokeCraftApi
import com.pidzama.smokecrafthookahapp.data.repository.SmokeCraftRepository
import com.pidzama.smokecrafthookahapp.domain.use_case.LoginUseCase
import com.pidzama.smokecrafthookahapp.utils.Constants.Network.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHookahApi(): SmokeCraftApi {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }).build()
            )
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SmokeCraftApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSmokeCraftRepository(
        smokeCraftApi: SmokeCraftApi,
        preferences: DataStoreRepository
    ): SmokeCraftRepository {
        return SmokeCraftRepository(
            smokeCraftApi = smokeCraftApi,
            preferences = preferences
        )
    }

    @Provides
    @Singleton
    fun providesLoginUseCase(repository: SmokeCraftRepository): LoginUseCase {
        return LoginUseCase(repository)
    }


}