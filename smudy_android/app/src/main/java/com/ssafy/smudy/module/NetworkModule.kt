package com.ssafy.smudy.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ssafy.data.BuildConfig
import com.ssafy.data.BuildConfig.BASE_URL
import com.ssafy.data.api.UserService
import com.ssafy.smudy.AuthInterceptor
import com.ssafy.smudy.retrofit_util.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthRetrofit

    @Provides
    @Singleton
    fun moshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun providesRetrofitClient(
        moshi: Moshi,
        authInterceptor: AuthInterceptor,
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    @Provides
    @Singleton
    @AuthRetrofit
    fun providesAuthRetrofitClient(
        moshi: Moshi
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

}