plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.huy.airport_app_java"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.huy.airport_app_java"
        minSdk = 24
        targetSdk = 35
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Lifecycle components (ViewModel, LiveData)
    implementation (libs.lifecycle.viewmodel)
    implementation (libs.lifecycle.livedata)
    implementation (libs.androidx.lifecycle.common.java8)

    // Room components
    implementation (libs.androidx.room.runtime)
    annotationProcessor (libs.androidx.room.compiler)

    // Navigation Component
    implementation (libs.androidx.navigation.fragment)
    implementation (libs.androidx.navigation.ui)

    // RecyclerView
    implementation (libs.androidx.recyclerview)

    // Charting Library (MPAndroidChart)
//    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // SQL Server JDBC Driver
    implementation(libs.jtds)

}