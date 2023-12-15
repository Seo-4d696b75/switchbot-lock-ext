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

rootProject.name = "switchbot-lock-ext"
include(":app")
include(":theme")
include(":ui")
include(":domain")
include(":api")
include(":data")
include(":widget")
