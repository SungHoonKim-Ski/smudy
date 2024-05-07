package com.ssafy.smudy.module

import android.content.Context
import com.ssafy.presentation.base.Test2
//import com.ssafy.util.TestM
import com.ssafy.util.spotify.SpotifyManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    @Singleton
    fun provideSpotifyManager(
        @ApplicationContext
        context: Context
    ) = SpotifyManager(context)

//    @Provides
//    @Singleton
//    fun provideTest(
//        @ApplicationContext context: Context
//    ): Test2{
//        return Test2(context)
//    }

}