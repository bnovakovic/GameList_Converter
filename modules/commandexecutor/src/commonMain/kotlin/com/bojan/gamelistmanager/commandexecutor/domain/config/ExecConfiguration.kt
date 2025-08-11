package com.bojan.gamelistmanager.commandexecutor.domain.config

/**
 * Sealed interface for the command execution configuration.
 */
sealed interface ExecConfiguration {
    /**
     * Configuration for running a ROM with RetroArch.
     * @param romPath The full path to the ROM file.
     * @param coreFullPath The full path to the RetroArch core file (.so, .dll, etc.).
     * @param retroArchExecutablePath The path or command for the RetroArch executable.
     */
    data class RunRom(val romPath: String, val coreFullPath: String, val retroArchExecutablePath: String) : ExecConfiguration
}