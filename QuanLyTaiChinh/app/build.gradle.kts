plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.quanlytaichinh"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.quanlytaichinh"
        minSdk = 25
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.swiperefreshlayout)
    implementation(libs.flexbox)
    implementation(libs.room.common)
    implementation(libs.room.runtime)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.lottie)

    implementation(libs.fragment)
    implementation(libs.appcompat.v161)
    implementation(libs.material.calendar.view)
    implementation("com.github.AnyChart:AnyChart-Android:1.1.5")
    implementation(libs.mpandroidchart)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    annotationProcessor(libs.room.compiler)
}