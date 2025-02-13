import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.lang.System.getProperty

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.treasure"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.treasure"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        resValue("string", "weatherapi_key", gradleLocalProperties(rootDir, providers).getProperty("weatherapi_key"))
        resValue("bool", "debug_mode", gradleLocalProperties(rootDir, providers).getProperty("debug_mode"))
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
    implementation (libs.commons.validator)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.room.runtime)
    implementation("com.google.firebase:firebase-bom:33.9.0")
    implementation("com.google.android.gms:play-services-auth:21.3.0")
    implementation("com.google.firebase:firebase-database")
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    annotationProcessor(libs.room.compiler)
    implementation(libs.converter.gson)
    implementation(libs.material.v180)
    implementation(libs.material.v190)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}