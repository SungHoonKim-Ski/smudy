package com.ssafy.smudy

import com.ssafy.data.datasource.datastore.PreferencesDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

private const val TAG = "AuthInterceptor"
class AuthInterceptor @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val accessToken = runBlocking {
            preferencesDataSource.getAccessToken()
        }

        if (accessToken != null) {
            request = chain.request().newBuilder()
                .addHeader("Authorization", accessToken)
                .build()
        }
        return chain.proceed(request)
    }
}