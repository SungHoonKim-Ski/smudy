package com.ssafy.smudy

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SmudyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val keyHash = Utility.getKeyHash(this)
        Log.d("TAG", "onCreate: $keyHash")
        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}