package com.ssafy.smudy

import com.ssafy.data.api.TokenService
import com.ssafy.data.datasource.datastore.PreferencesDataSource
import com.ssafy.data.model.auth.RefreshToken
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
    private val tokenService: TokenService
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking { preferencesDataSource.getRefreshToken() }
        val accessToken = runBlocking { preferencesDataSource.getAccessToken() }
        if (refreshToken == null || accessToken == null) {
            response.close()
            return null
        }

        return runBlocking {
            val tokenResponse = tokenService.reissueToken(accessToken,RefreshToken(refreshToken))
            if (tokenResponse.isSuccessful && tokenResponse.body() != null) {
                preferencesDataSource.setToken(tokenResponse.body()!!.data!!)
                val header = "${tokenResponse.body()!!.data!!.grantType} ${tokenResponse.body()!!.data!!.accessToken}"
                response.request
                    .newBuilder()
                    .header("Authorization", header)
                    .build()
            } else {
                // 토큰 삭제
                preferencesDataSource.deleteToken()
                null
            }
        }
    }
}