// settings.gradle.kts
pluginManagement {
    repositories {
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
    }
}


rootProject.name = "Authentication"
include(":app")
