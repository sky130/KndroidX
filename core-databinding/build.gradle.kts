plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
    id("kotlin-kapt")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.kndroidx"
            artifactId = "core-databinding"
            version = libs.versions.kndroidx.project.get()

            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        mavenLocal()
    }
}

android {
    namespace = "com.github.kndroidx"
    compileSdk = 36

    defaultConfig {
        minSdk = 21
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

kapt {
    generateStubs = true
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)

    implementation(project(":core"))
}