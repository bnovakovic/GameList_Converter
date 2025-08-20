package com.bojan.gamelistmanager.commandexecutor.domain.config

import java.io.File

/**
 * Configuration for executing command line command.
 */
sealed class ExecConfiguration {

    /**
     * Runs selected ROM with specific core in RetroArch.
     *
     * @param romPath path to the ROM.
     * @param retroArchExecutablePath retroArch executable path.
     * @param corePath core full path.
     */
    data class RunRom(
        val romPath: File,
        val retroArchExecutablePath: File,
        val corePath: File
    ) : ExecConfiguration()

    data object FindRetroArchPath : ExecConfiguration()
}