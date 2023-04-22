package com.tanya.newsapp.di.module

import android.content.Context
import androidx.room.Room
import com.tanya.newsapp.NewsApplication
import com.tanya.newsapp.data.local.AppDatabase
import com.tanya.newsapp.data.api.KeyInterceptor
import com.tanya.newsapp.data.api.NetworkService
import com.tanya.newsapp.di.ApplicationContext
import com.tanya.newsapp.di.BaseUrl
import com.tanya.newsapp.ui.DispatcherProviderImpl
import com.tanya.newsapp.utils.Logger
import com.tanya.newsapp.utils.LoggerImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: NewsApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun providesAppDatabase() : AppDatabase = Room.databaseBuilder(application.applicationContext,
        AppDatabase::class.java, "newsDb").build()

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        client: OkHttpClient
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun createHttpClient() : OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(KeyInterceptor())
        return builder.build()
    }

    @Provides
    @Singleton
    fun createDispatcher() : DispatcherProviderImpl = DispatcherProviderImpl()

    @Provides
    @Singleton
    fun createLogger() : Logger = LoggerImpl()

}