plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kover)
}

kotlin {
    jvm("desktop")
}

dependencies {
    commonMainImplementation(projects.utils)
    commonMainImplementation(libs.kotlinx.coroutines.core)
}