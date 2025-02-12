package com.ssafy.data.datasource.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ssafy.data.model.auth.TokenResponse
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun setToken(token: TokenResponse) {
        dataStore.edit {
            it[ACCESS_TOKEN_KEY] = "${token.grantType} ${token.accessToken}"
            it[REFRESH_TOKEN_KEY] = token.refreshToken
        }
    }

    suspend fun getAccessToken(): String? {
        return dataStore.data.map {
            it[ACCESS_TOKEN_KEY]
        }.firstOrNull()
    }

    suspend fun getRefreshToken(): String? {
        return dataStore.data.map {
            it[REFRESH_TOKEN_KEY]
        }.firstOrNull()
    }

    suspend fun deleteToken(){
        dataStore.edit {
            it.remove(ACCESS_TOKEN_KEY)
            it.remove(REFRESH_TOKEN_KEY)
        }
    }

    suspend fun setUserInfo(name:String,image:String){
        dataStore.edit {
            it[USER_NAME] = name
            it[USER_IMAGE] = image
        }
    }

    suspend fun getUserName():String?{
        return dataStore.data.map {
            it[USER_NAME]
        }.firstOrNull()
    }

    suspend fun getUserImage():String?{
        return dataStore.data.map {
            it[USER_IMAGE]
        }.firstOrNull()
    }
    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_IMAGE = stringPreferencesKey("user_image")
    }
}