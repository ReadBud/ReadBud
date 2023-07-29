plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-parcelize")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
    id ("com.google.firebase.crashlytics")
    id ("com.google.gms.google-services")
}

android {
    namespace = "com.binayshaw7777.readbud"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.binayshaw7777.readbud"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
        named("debug") {

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    configurations.implementation {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
}

dependencies {

    val navVersion = "2.6.0"
    val lifecycleVersion = "2.6.1"
    val cameraxVersion = "1.0.2"

    implementation ("androidx.core:core-ktx:1.8.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation ("androidx.activity:activity-compose:1.7.2")
    implementation (platform("androidx.compose:compose-bom:2022.10.00"))
    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.compose.ui:ui-graphics")
    implementation ("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.compose.runtime:runtime:1.4.3")
    implementation ("androidx.compose.runtime:runtime-livedata:1.4.3")


    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation (platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4")
    debugImplementation ("androidx.compose.ui:ui-tooling")
    debugImplementation ("androidx.compose.ui:ui-test-manifest")

    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation ("com.google.accompanist:accompanist-insets:0.14.0")

    //Material 3
    implementation ("androidx.compose.material3:material3:1.2.0-alpha03")
    implementation ("androidx.compose.material3:material3-window-size-class:1.2.0-alpha03")

    //Navigation
    implementation ("androidx.navigation:navigation-compose:$navVersion")

    // Lifecycle
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

    //CameraX
    implementation ("androidx.camera:camera-core:${cameraxVersion}")
    implementation ("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation ("androidx.camera:camera-lifecycle:${cameraxVersion}")
    implementation ("androidx.camera:camera-view:1.3.0-beta01")

    //Camera Permission
    implementation ("com.google.accompanist:accompanist-permissions:0.19.0")

    //MLKit
    implementation ("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")

    //DataStore (SharedPrefs)
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    //Image Loading
    implementation("io.coil-kt:coil-compose:2.2.2")

    //Page Curls
    implementation ("io.github.oleksandrbalan:pagecurl:1.3.0")

    //Gson - JSON
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Room
    implementation ("androidx.room:room-runtime:2.5.2")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.room:room-ktx:2.5.2")
    implementation ("androidx.compose.runtime:runtime-livedata:1.4.3")
    annotationProcessor ("androidx.room:room-compiler:2.5.2")
    kapt ("androidx.room:room-compiler:2.5.2")

    //hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-work:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.work:work-runtime-ktx:2.8.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))

    //Crashlytics and Analytics libraries
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")

    //Swipe Gesture
    implementation ("me.saket.swipe:swipe:1.2.0")

    //Splash Screen
    implementation ("androidx.core:core-splashscreen:1.0.1")
}