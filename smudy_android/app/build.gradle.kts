import java.io.FileInputStream
import java.util.Properties


plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    //hilt
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    //ksp
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

var properties = Properties()
properties.load(FileInputStream("local.properties"))

android {
    namespace = "com.ssafy.smudy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ssafy.smudy"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", properties.getProperty("BASE_URL"))

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // https://github.com/square/retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // https://github.com/square/okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // https://github.com/square/retrofit/tree/master/retrofit-converters/gson
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Glide 사용
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    //viewmodel dependency 추가
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    //lifecycle scope dependency
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    //framework ktx dependency 추가
    implementation ("androidx.fragment:fragment-ktx:1.6.2")

    // 코루틴
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    //hilt
    implementation("com.google.dagger:hilt-android:2.49")
    implementation("com.android.identity:identity-credential-android:20231002")


    // DataStore
    implementation("androidx.datastore:datastore-core:1.1.0")

    // Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")

    // viwePager2
    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    //lottie
    implementation ("com.airbnb.android:lottie:6.4.0")

    //paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    kapt ("com.google.dagger:hilt-compiler:2.48")
}

