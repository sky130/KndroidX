// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    kotlin("kapt") version "1.9.10"
    id("maven-publish")
    alias(libs.plugins.android.library) apply false
}

