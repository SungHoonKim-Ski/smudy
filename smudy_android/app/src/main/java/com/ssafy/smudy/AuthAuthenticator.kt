package com.ssafy.smudy

import com.ssafy.data.api.TokenService
import com.ssafy.data.datasource.datastore.PreferencesDataSource
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
        val refreshToken = runBlocking {
            preferencesDataSource.getRefreshToken()
        }

        if (refreshToken == null) {
            response.close()
            return null
        }

        return runBlocking {
            val tokenResponse = tokenService.reissueToken(refreshToken)
            if (tokenResponse.isSuccessful && tokenResponse.body() != null) {
                preferencesDataSource.setToken(tokenResponse.body()!!.data!!)

                response.request
                    .newBuilder()
                    .header("Authorization", tokenResponse.body()!!.data!!.accessToken)
                    .build()
            } else {
                // 토큰 삭제
                preferencesDataSource.deleteToken()
                null
            }
        }
    }
}