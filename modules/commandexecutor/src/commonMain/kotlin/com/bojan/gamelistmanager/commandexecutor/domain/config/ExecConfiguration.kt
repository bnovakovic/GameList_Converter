package com.bojan.gamelistmanager.commandexecutor.domain.config

/**
 * Configuration for executing command line command.
 */
sealed class ExecConfiguration {

    /**
     * Runs selected ROM with specific core in RetroArch.
     *
     * @param romPath Path to the ROM.
     * @param coreFileName Filename of the core.
     * @param retroArchDir RetroArch path.
     */
    data class RunRom(val romPath: String, val coreFileName: String, val retroArchDir: String) : ExecConfiguration()
}