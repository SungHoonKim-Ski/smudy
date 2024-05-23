package com.ssafy.util.spotify

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.ssafy.util.BuildConfig
import kotlin.math.log


private const val TAG = "SpotifyManager"

class SpotifyManager(private val context: Context) {

    interface AppRemoteCallbackListener{
        fun onAppRemoteInitialized()
    }

    private lateinit var appRemoteListener: AppRemoteCallbackListener

    fun setAppRemoteCallbackListener(listener: AppRemoteCallbackListener){
        appRemoteListener = listener
    }

    private lateinit var builder: AuthorizationRequest.Builder
    private lateinit var spotifyAppRemote: SpotifyAppRemote
    private val clientID = BuildConfig.SPOTIFY_DEVELOPER_ID
    private val redirectUri = "https://smudy/callback"
    private val REQUEST_CODE = 1337

    fun spotifyAuthenticate(activity: Activity) {
        builder = AuthorizationRequest.Builder(
            clientID,
            AuthorizationResponse.Type.TOKEN,
            redirectUri
        )

        builder.setScopes(arrayOf("user-read-private", "user-read-email"))
        val request = builder.build()

        AuthorizationClient.openLoginActivity(activity, REQUEST_CODE, request)

    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, data)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    val token = response.accessToken
                    Log.d(TAG, "onActivityResult: $token")
                    apiConnect()
                }

                AuthorizationResponse.Type.ERROR -> {
                    Log.d(TAG, "onActivityResult: error")
                }

                else -> {
                    Log.d(TAG, "onActivityResult: $response")
                }
            }
        }
    }



    private fun apiConnect() {
        val connectionParams = ConnectionParams.Builder(clientID)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(context, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                appRemoteListener.onAppRemoteInitialized()
//                connected()
            }

            override fun onFailure(throwable: Throwable) {
                Log.e(TAG, "onFailure: ${throwable.message}")
            }
        })
    }

    fun playTrack(songId: String) {
        spotifyAppRemote.let {
            it.playerApi.play("spotify:track:$songId").setResultCallback {
                Log.d(TAG, "playTrack: $it")
            }?.setErrorCallback {
                Log.e(TAG, "pauseTrack: $it", )
            }
        }
    }

    fun seekTo(startTime: Long){
        spotifyAppRemote.playerApi.seekTo(startTime)
    }

    fun pauseTrack() {
        if(this::spotifyAppRemote.isInitialized){
            spotifyAppRemote.let {
                it.playerApi.pause().setResultCallback {
                }?.setErrorCallback {
                    Log.e(TAG, "pauseTrack: $it", )
                }
            }
        }
    }

    fun resumeTrack() {
        spotifyAppRemote.let {
            it.playerApi.resume().setResultCallback {
            }?.setErrorCallback {
                Log.e(TAG, "pauseTrack: $it", )
            }
        }
    }

    private fun connected() {
        spotifyAppRemote.let {
            it.playerApi.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")
            it.playerApi.subscribeToPlayerState().setEventCallback { ps ->
                val track = ps.track
                Log.e(TAG, "onEvent: ${track.name}")
            }
        }
    }


    fun disconnect() {
        spotifyAppRemote.let {
            SpotifyAppRemote.disconnect(it)
        }
    }

}