plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm("desktop")
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.org.json)
}