package com.ssafy.smudy.module

import android.content.Context
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

}