pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Lichtblick"

// app  = Android-Oberfläche (Jetpack Compose)
// core = Inhalte und Logik in reinem Kotlin (Tests, Übungen, Impulse, Links)
include(":app")
include(":core")
