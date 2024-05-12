package com.ssafy.smudy.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ssafy.data.BuildConfig.BASE_URL
import com.ssafy.data.api.AuthService
import com.ssafy.data.api.MusicService
import com.ssafy.data.api.PronounceService
import com.ssafy.data.api.StudyService
import com.ssafy.data.api.TokenService
import com.ssafy.data.api.UserService
import com.ssafy.smudy.AuthAuthenticator
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
import retrofit2.create
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
        authAuthenticator: AuthAuthenticator
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(authAuthenticator)
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
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideTokenService(@AuthRetrofit retrofit: Retrofit): TokenService =
        retrofit.create(TokenService::class.java)

    @Provides
    @Singleton
    fun provideMusicService(retrofit: Retrofit): MusicService =
        retrofit.create(MusicService::class.java)

    @Provides
    @Singleton
    fun provideStudyService(retrofit: Retrofit): StudyService =
        retrofit.create(StudyService::class.java)

    @Provides
    @Singleton
    fun providePronounceService(retrofit: Retrofit): PronounceService =
        retrofit.create(PronounceService::class.java)
}