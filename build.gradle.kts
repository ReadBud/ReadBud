buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version ("8.5.0") apply false
    id("com.android.library") version ("8.5.0") apply false
    id("org.jetbrains.kotlin.android") version ("1.8.10") apply false
    id("com.google.dagger.hilt.android") version ("2.44") apply false
    id("com.google.firebase.crashlytics") version ("2.9.7") apply false
}