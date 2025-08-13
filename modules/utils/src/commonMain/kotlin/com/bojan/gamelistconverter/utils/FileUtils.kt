package com.bojan.gamelistconverter.utils

import java.io.File

fun getWorkingDirectory(): String = "${getOsAppDataDirectory()}${getFileSeparator()}GameListConverter"
private fun getOsAppDataDirectory(): File {
    return when (getOs()) {
        JvmOs.WINDOWS -> File(System.getenv("AppData"))
        JvmOs.LINUX -> File(getUserHome(),".config")
        JvmOs.MAC -> File(getUserHome(), "/Library/Application Support/")
    }
}

fun getUserHome() = File(System.getProperty("user.home"))

fun getOsName(): String = System.getProperty("os.name")

fun getOs(): JvmOs {
    val osName = getOsName().lowercase()
    if (osName.contains("windows")) return JvmOs.WINDOWS
    if (osName.contains("mac")) return JvmOs.MAC
    if (osName.contains("nux")) return JvmOs.LINUX
    throw IllegalArgumentException("Operating system $osName not supported.")
}

enum class JvmOs { WINDOWS, LINUX, MAC }

fun getFileSeparator(): String = System.getProperty("file.separator")

fun getCoreExtension(): String = when (getOs()) {
    JvmOs.LINUX -> { SO_EXTENSION }
    JvmOs.MAC -> { DYLIB_EXTENSION }
    JvmOs.WINDOWS -> { DLL_EXTENSION }
}

fun getExecutableExtension() : String = when(getOs()) {
    JvmOs.LINUX -> { LINUX_EXECUTABLE_EXTENSION }
    JvmOs.MAC -> { MACOS_EXECUTABLE_EXTENSION }
    JvmOs.WINDOWS -> { WINDOWS_EXECUTABLE_EXTENSION }
}

fun String.resolveHomePath(): String {
    return when {
        this.startsWith(LINUX_MAC_HOME_SYMBOL) -> this.replace(LINUX_MAC_HOME_SYMBOL, getUserHome().toString())
        this.startsWith(WINDOWS_HOME_PATTERN) -> this.replace(WINDOWS_HOME_PATTERN, getUserHome().toString())
        else -> this
    }
}


const val SO_EXTENSION = "so"
const val DLL_EXTENSION = "dll"
const val DYLIB_EXTENSION = "dylib"
const val WINDOWS_EXECUTABLE_EXTENSION = "exe"
const val MACOS_EXECUTABLE_EXTENSION = "app"
const val LINUX_EXECUTABLE_EXTENSION = "sh"
const val LINUX_MAC_HOME_SYMBOL = "~"
const val WINDOWS_HOME_PATTERN = "%USERPROFILE%"