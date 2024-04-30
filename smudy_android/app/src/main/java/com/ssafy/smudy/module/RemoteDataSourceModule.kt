package com.ssafy.smudy.module

import com.ssafy.data.datasource.auth.AuthRemoteDataSource
import com.ssafy.data.datasource.auth.AuthRemoteDataSourceImpl
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

}