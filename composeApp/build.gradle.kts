import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

val softwareVersion: String by extra { "1.0.8" }

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.mvvm.core)
            implementation(libs.mvvm.compose)
            implementation(projects.gameListProvider)
            implementation(projects.listExporter)
            implementation(projects.retroArchInfoLoader)
            implementation(projects.commandexecutor)
            implementation(projects.utils)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.coil)
            implementation(libs.compose.material.icons)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}


compose.desktop {
    application {
        System.getenv("JDK_17")
        mainClass = "MainKt"

        buildTypes.release.proguard {
            isEnabled.set(false)
            configurationFiles.from(File(rootDir, "proguard-rules.pro"))
            joinOutputJars.set(true)
        }

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "GameListConverter"
            packageVersion = softwareVersion
            description = "Convert your gamelist.xml to RetroArch playlist file"
            copyright = "© 2024 Bojan Novakovic. All rights reserved."
            vendor = "Bojan Novaković"
            licenseFile.set(File(rootDir, "LICENSE"))
            includeAllModules = true

            val iconsFolder = project.file("icons")
            macOS {
                iconFile.set(iconsFolder.resolve("launcher_icon_1.icns"))
            }
            linux {
                iconFile.set(iconsFolder.resolve("launcher_icon_1.png"))
            }
            windows {
                iconFile.set(iconsFolder.resolve("launcher_icon_1.ico"))
            }
        }
    }
}

tasks.register("getVersionName") {
    doLast {
        println(softwareVersion)
    }
}
