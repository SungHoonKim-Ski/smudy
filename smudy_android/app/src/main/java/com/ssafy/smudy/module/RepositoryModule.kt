package com.ssafy.smudy.module

import com.ssafy.data.repository.AuthRepositoryImpl
import com.ssafy.data.repository.MusicRepositoryImpl
import com.ssafy.domain.repository.AuthRepository
import com.ssafy.domain.repository.MusicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindMusicRepository(musicRepositoryImpl: MusicRepositoryImpl): MusicRepository
}