import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.pictureoftheday"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pictureoftheday"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    val myProperties = Properties().apply {
        load(FileInputStream(File(rootProject.rootDir, "apikey.properties")))
    }
    val key = myProperties.getProperty("NASA_API_KEY")

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "NASA_API_KEY", key)
        }
        getByName("release") {
            buildConfigField("String", "NASA_API_KEY", key)
        }
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

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.6.2")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
//Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin")
    implementation ("androidx.core:core-ktx:1.1.0")

//Lifecycle and ViewModel (архитектурные компоненты)
    implementation ("androidx.lifecycle:lifecycle-extensions:2.1.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0")
//Koil image download (аналог Picasso и Glide, написанный на Kotlin)
    implementation(libs.coil.kt.coil)


    implementation(libs.coil.svg)
    implementation(libs.coil.network.okhttp)
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")

}