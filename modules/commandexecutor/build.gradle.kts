plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm("desktop")
}

dependencies {
    commonMainImplementation(projects.utils)
}