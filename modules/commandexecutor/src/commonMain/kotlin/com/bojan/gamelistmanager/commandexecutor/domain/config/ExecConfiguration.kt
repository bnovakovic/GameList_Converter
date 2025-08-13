package com.bojan.gamelistmanager.commandexecutor.domain.config

/**
 * Configuration for executing command line command.
 */
sealed class ExecConfiguration {

    /**
     * Runs selected ROM with specific core in RetroArch.
     *
     * @param romPath path to the ROM.
     * @param retroArchDir retroArch path.
     * @param corePath core full path.
     */
    data class RunRom(
        val romPath: String,
        val retroArchDir: String,
        val corePath: String
    ) : ExecConfiguration()
}