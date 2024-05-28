// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    dependencies{
        classpath ("androidx.navigation.safeargs.kotlin:androidx.navigation.safeargs.kotlin.gradle.plugin:2.7.7")
    }
}

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}