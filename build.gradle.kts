buildscript {
    val agp_version by extra("8.0.0")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
//    id("com.github.dcendents:android-maven-gradle-plugin") version "1.3" apply false
}

