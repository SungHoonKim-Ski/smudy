package com.ssafy.smudy.module

import com.ssafy.data.datasource.auth.AuthRemoteDataSource
import com.ssafy.data.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.data.datasource.music.MusicRemoteDataSource
import com.ssafy.data.datasource.music.MusicRemoteDataSourceImpl
import com.ssafy.data.datasource.study.remote.StudyRemoteDataSource
import com.ssafy.data.datasource.study.remote.StudyRemoteDataSourceImpl
import com.ssafy.data.datasource.study.remote.pronounce.PronounceRemoteDataSource
import com.ssafy.data.datasource.study.remote.pronounce.PronounceRemoteDataSourceImpl
import com.ssafy.data.datasource.study.remote.shuffle.ShuffleRemoteDataSource
import com.ssafy.data.datasource.study.remote.shuffle.ShuffleRemoteDataSourceImpl
import com.ssafy.data.datasource.user.remote.UserInfoRemoteDataSource
import com.ssafy.data.datasource.user.remote.UserInfoRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    abstract fun bindUserInfoRemoteDataSource(userInfoRemoteDataSourceImpl: UserInfoRemoteDataSourceImpl): UserInfoRemoteDataSource

    @Binds
    abstract fun bindStudyRemoteDataSource(studyRemoteDataSourceImpl: StudyRemoteDataSourceImpl): StudyRemoteDataSource

    @Binds
    abstract fun bindMusicRemoteDataSource(musicRemoteDataSourceImpl: MusicRemoteDataSourceImpl): MusicRemoteDataSource

    @Binds
    abstract fun bindPronounceRemoteDataSource(pronounceRemoteDataSourceImpl: PronounceRemoteDataSourceImpl): PronounceRemoteDataSource

    @Binds
    abstract fun bindShuffleRemoteDataSource(shuffleRemoteDataSourceImpl: ShuffleRemoteDataSourceImpl): ShuffleRemoteDataSource

}