import java.util.Properties
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.kapt)
    alias(libs.plugins.parcelize)

}

android {
    namespace = "com.ssafy.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
        }
        manifestPlaceholders["NATIVE_APP_KEY"] = localProperties["NATIVE_APP_KEY"] as String

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(":util"))

    implementation(libs.bundles.androidx)
    implementation(libs.hilt.android)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.livedata.ktx)
//    ksp(libs.hilt.compiler)
//    ksp(libs.dagger.compiler)

    kapt(libs.hilt.compiler)
    implementation(libs.bundles.hilt)

    implementation(libs.bundles.presentationBundle)

    implementation(libs.kakao)
    // Navigation
    implementation(libs.bundles.navigation)
    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}