package com.pidzama.smokecrafthookahapp.di


import android.app.Application
import androidx.room.Room
import com.pidzama.smokecrafthookahapp.data.local.RecipeDao
import com.pidzama.smokecrafthookahapp.data.local.RecipeDataBase
import com.pidzama.smokecrafthookahapp.data.network.AccessTokenInterceptor
import com.pidzama.smokecrafthookahapp.data.network.AuthAuthenticator
import com.pidzama.smokecrafthookahapp.data.network.RefreshTokenInterceptor
import com.pidzama.smokecrafthookahapp.data.network.RefreshTokenService
import com.pidzama.smokecrafthookahapp.data.network.SmokeCraftApi
import com.pidzama.smokecrafthookahapp.data.repository.DataStoreRepository
import com.pidzama.smokecrafthookahapp.data.repository.JwtTokenDataStore
import com.pidzama.smokecrafthookahapp.data.repository.RecipeRepositoryImpl
import com.pidzama.smokecrafthookahapp.domain.model.RecipeMapper
import com.pidzama.smokecrafthookahapp.domain.repository.RecipeRepository
import com.pidzama.smokecrafthookahapp.domain.use_case.AppUseCase
import com.pidzama.smokecrafthookahapp.domain.use_case.CreateOrderUseCase
import com.pidzama.smokecrafthookahapp.domain.use_case.GetAllRecipesInDataBaseUseCase
import com.pidzama.smokecrafthookahapp.domain.use_case.GetAllTobaccosListUseCase
import com.pidzama.smokecrafthookahapp.domain.use_case.InsertRecipeToArchiveUseCase
import com.pidzama.smokecrafthookahapp.domain.use_case.LoginUseCase
import com.pidzama.smokecrafthookahapp.domain.use_case.OrderInfoUseCase
import com.pidzama.smokecrafthookahapp.domain.use_case.OrdersListUseCase
import com.pidzama.smokecrafthookahapp.domain.use_case.RecipesUseCase
import com.pidzama.smokecrafthookahapp.domain.use_case.ReduceRecipeUseCase
import com.pidzama.smokecrafthookahapp.utils.Constants.Database.NAME_DATABASE
import com.pidzama.smokecrafthookahapp.utils.Constants.Network.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    //создаем экземпляр ретрофита для авторизированного юзера
    @[Provides Singleton]
    fun provideAuthenticationApi(@AuthenticatedClient okHttpClient: OkHttpClient): SmokeCraftApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(SmokeCraftApi::class.java)
    }

    //создаем экземпляр ретрофита для обновления токена
    @[Provides Singleton]
    fun provideRetrofit(@TokenRefreshClient okHttpClient: OkHttpClient): RefreshTokenService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RefreshTokenService::class.java)
    }

    @Provides
    @Singleton
    fun provideSmokeCraftRepository(
        smokeCraftApi: SmokeCraftApi,
        jwtToken: JwtTokenDataStore,
        preferences: DataStoreRepository,
        recipeDao: RecipeDao
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            apiService = smokeCraftApi,
            jwtTokenManager = jwtToken,
            dataStore = preferences,
            recipeDao = recipeDao
        )
    }

    @Provides
    @Singleton
    fun provideRecipesUseCases(
        recipeRepository: RecipeRepository,
        mapper: RecipeMapper,
    ): AppUseCase {
        return AppUseCase(
            login = LoginUseCase(recipeRepository),
            recipes = RecipesUseCase(recipeRepository, mapper),
            getAllTobaccosList = GetAllTobaccosListUseCase(recipeRepository),
            getAllRecipesInDataBaseUseCase = GetAllRecipesInDataBaseUseCase(recipeRepository),
            insertRecipeToArchiveUseCase = InsertRecipeToArchiveUseCase(recipeRepository),
            reduceRecipeUseCase = ReduceRecipeUseCase(recipeRepository),
            ordersList = OrdersListUseCase(recipeRepository),
            createOrder = CreateOrderUseCase(recipeRepository),
            getInfoOrder = OrderInfoUseCase(recipeRepository)
        )
    }

    @Provides
    @Singleton
    fun provideRecipesDataBase(
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
    fun provideRecipesDao(
        recipeDataBase: RecipeDataBase
    ): RecipeDao = recipeDataBase.RecipeDao()

    //настройка OkHttpClient для запроса токена доступа
    @[Provides Singleton AuthenticatedClient]
    fun provideAccessOkHttpClient(
        accessTokenInterceptor: AccessTokenInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .authenticator(authAuthenticator)
            .addInterceptor(accessTokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    //настройка OkHttpClient для запроса токена обновления
    @[Provides Singleton TokenRefreshClient]
    fun provideRefreshOkHttpClient(
        refreshTokenInterceptor: RefreshTokenInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(refreshTokenInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @[Provides Singleton PublicClient]
    fun provideUnauthenticatedOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    //определяем два квалификатора для запроса токена досутпа
    //и запроса токена обновления
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthenticatedClient

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class TokenRefreshClient

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PublicClient
}
