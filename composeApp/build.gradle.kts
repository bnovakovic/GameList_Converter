import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.mvvm.core)
            implementation(libs.mvvm.compose)
            implementation(projects.gameListProvider)
            implementation(projects.listExporter)
            implementation(projects.retroArchInfoLoader)
            implementation(libs.kotlinx.coroutines.core)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"
        buildTypes.release.proguard {
            configurationFiles.from(File(rootDir, "proguard-rules.pro"))
        }
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "GameListConverter"
            packageVersion = "1.0.0"
            description = "Convert your gamelist.xml to RetroArch playlist file"
            copyright = "© 2024 Bojan Novakovic. All rights reserved."
            vendor = "Bojan Novaković"
            licenseFile.set(File(rootDir, "LICENSE"))
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
