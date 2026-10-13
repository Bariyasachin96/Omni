// The repositories as Android Studio's current project template states them:
// Google's Maven only for the groups it publishes, so Gradle never asks it for
// anything else. The version catalog, gradle/libs.versions.toml, is picked up
// by Gradle on its own (as `libs`).
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}
rootProject.name = "EasyVoice"
include(":app")
include(":baselineprofile")
