package com.ssafy.smudy.module

import com.ssafy.data.datasource.study.remote.StudyRemoteDataSourceImpl
import com.ssafy.data.repository.AuthRepositoryImpl
import com.ssafy.data.repository.MusicRepositoryImpl
import com.ssafy.data.repository.StudyRepositoryImpl
import com.ssafy.data.repository.UserRepositoryImpl
import com.ssafy.domain.repository.AuthRepository
import com.ssafy.domain.repository.MusicRepository
import com.ssafy.domain.repository.StudyRepository
import com.ssafy.domain.repository.UserRepository
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

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindStudyRepository(studyRepositoryImpl: StudyRepositoryImpl): StudyRepository

}