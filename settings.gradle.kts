pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "KndroidX"
include(":app")
include(":core")
include(":core-databinding")
include(":wear-tile")
include(":wear-smooth-recycler")
include(":recycler-live-adapter")
