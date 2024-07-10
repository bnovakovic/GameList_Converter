plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kover)
}

kotlin {
    jvm("desktop")
}

dependencies {
    commonMainImplementation(libs.kotlin.serialization)
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonTestImplementation(libs.junit)
}