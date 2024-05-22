@file:Suppress("DSL_SCOPE_VIOLATION")
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.daggerHilt)
}

fun getProperty(propertyKey: String): String = gradleLocalProperties(rootDir, providers).getProperty(propertyKey)

android {
    namespace = "com.ssafy.smudy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ssafy.smudy"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "NATIVE_APP_KEY", getProperty("NATIVE_APP_KEY"))

        manifestPlaceholders["redirectSchemeName"] = "http://localhost:8888"
        manifestPlaceholders["redirectHostName"] = "callback"
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

    buildFeatures{
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    hilt {
        enableAggregatingTask = true
    }

    packaging{
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,DEPENDENCIES,LICENSE.md,NOTICE.md,io.netty.versions.properties,annotation.kotlin_builtins,INDEX.LIST}"
            excludes += "/META-INF/gradle/{incremental.annotation.processors}"
        }
    }
}

dependencies {

    implementation(project(":presentation"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":util"))

    implementation(libs.bundles.androidx)
    testImplementation(libs.bundles.testing)

    // Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.kakao)

    // Retrofit
    implementation(libs.bundles.retrofit)

    // DataStore
    implementation(libs.dataStore)

    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("androidx.test:runner:1.4.0")
    androidTestImplementation ("androidx.test:rules:1.4.0")


}

