package com.ssafy.smudy.module

import com.ssafy.data.datasource.study.remote.StudyRemoteDataSourceImpl
import com.ssafy.data.repository.AuthRepositoryImpl
import com.ssafy.data.repository.ExpressRepositoryImpl
import com.ssafy.data.repository.MusicRepositoryImpl
import com.ssafy.data.repository.PronounceRepositoryImpl
import com.ssafy.data.repository.ShuffleRepositoryImpl
import com.ssafy.data.repository.StudyHistoryRepositoryImpl
import com.ssafy.data.repository.StudyRepositoryImpl
import com.ssafy.data.repository.UserRepositoryImpl
import com.ssafy.domain.repository.AuthRepository
import com.ssafy.domain.repository.ExpressRepository
import com.ssafy.domain.repository.MusicRepository
import com.ssafy.domain.repository.PronounceRepository
import com.ssafy.domain.repository.ShuffleRepository
import com.ssafy.domain.repository.StudyHistoryRepository
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

    @Binds
    abstract fun bindPronounceRepository(pronounceRepositoryImpl: PronounceRepositoryImpl): PronounceRepository

    @Binds
    abstract fun bindShuffleRepository(shuffleRepositoryImpl: ShuffleRepositoryImpl): ShuffleRepository

    @Binds
    abstract fun bindExpressRepository(expressRepositoryImpl: ExpressRepositoryImpl): ExpressRepository

    @Binds
    abstract fun bindStudyHistoryRepository(studyHistoryRepositoryImpl: StudyHistoryRepositoryImpl): StudyHistoryRepository
}