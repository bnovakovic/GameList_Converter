plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm("desktop")
}

dependencies {
    commonMainImplementation(projects.utils)
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonTestImplementation(libs.junit)
}