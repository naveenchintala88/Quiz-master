plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.quizmaster"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.quizmaster"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.auth.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    
    implementation (libs.androidx.core.splashscreen)

    implementation (libs.androidx.navigation.compose)

    implementation (libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)

    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    implementation (libs.androidx.foundation)

    implementation (libs.accompanist.systemuicontroller)

    implementation (libs.lottie.compose)

    implementation (libs.ui)
    implementation (libs.androidx.material)
    implementation (libs.ui.tooling)
    implementation (libs.androidx.lifecycle.runtime.compose)

    implementation (libs.retrofit.v2110)
    implementation (libs.converter.gson.v2110)

    implementation (libs.kotlinx.coroutines.android.v173)


    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)

    //noinspection KaptUsageInsteadOfKsp
    kapt (libs.androidx.room.compiler)


    implementation (libs.androidx.runtime.livedata.v140)


    implementation (libs.jetbrains.kotlinx.coroutines.android)


    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

}