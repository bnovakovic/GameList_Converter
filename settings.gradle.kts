rootProject.name = "GamelistConverter"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

val modulesDirectory = file("${rootDir}/modules/")
fileTree(modulesDirectory)
    .matching { include("*/build.gradle.kts") }
    .forEach { file ->
        val parent = file.parentFile
        val projectName = ":" + parent.name
        include(projectName)
        project(projectName).projectDir = parent
    }

include(":composeApp")