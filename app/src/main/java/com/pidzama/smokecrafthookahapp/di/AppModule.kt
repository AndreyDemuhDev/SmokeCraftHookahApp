package com.pidzama.smokecrafthookahapp.di


import android.app.Application
import androidx.room.Room
import com.pidzama.smokecrafthookahapp.data.local.RecipeDao
import com.pidzama.smokecrafthookahapp.data.local.RecipeDataBase
import com.pidzama.smokecrafthookahapp.data.network.SmokeCraftApi
import com.pidzama.smokecrafthookahapp.data.repository.DataStoreRepository
import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import com.pidzama.smokecrafthookahapp.data.repository.RecipeRepositoryImpl
import com.pidzama.smokecrafthookahapp.domain.model.RecipeMapper
import com.pidzama.smokecrafthookahapp.domain.use_case.*
import com.pidzama.smokecrafthookahapp.utils.Constants.Database.NAME_DATABASE
//import com.pidzama.smokecrafthookahapp.data.repository.SmokeCraftRepository
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
        preferences: DataStoreRepository,
        recipeDao: RecipeDao
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            apiService = smokeCraftApi,
            preferences = preferences,
            recipeDao = recipeDao
        )
    }

    @Provides
    @Singleton
    fun provideGamesUseCases(
        gamesRepository: RecipeRepository,
        mapper: RecipeMapper,
    ): AppUseCase {
        return AppUseCase(
            login = LoginUseCase(gamesRepository),
            recipes = RecipesUseCase(gamesRepository, mapper),
            getAllRecipesInDataBaseUseCase = GetAllRecipesInDataBaseUseCase(gamesRepository),
            insertRecipeToArchiveUseCase = InsertRecipeToArchiveUseCase(gamesRepository),
            reduceRecipeUseCase = ReduceRecipeUseCase(gamesRepository)
        )
    }

    @Provides
    @Singleton
    fun provideGamesDataBase(
        application: Application
    ): RecipeDataBase {
        return Room.databaseBuilder(
            context = application,
            klass = RecipeDataBase::class.java,
            name = NAME_DATABASE
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideGamesDao(
        gamesDataBase: RecipeDataBase
    ): RecipeDao = gamesDataBase.RecipeDao()
}
