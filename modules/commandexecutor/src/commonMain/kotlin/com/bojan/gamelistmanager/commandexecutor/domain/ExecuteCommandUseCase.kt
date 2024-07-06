package com.bojan.gamelistmanager.commandexecutor.domain

import com.bojan.gamelistconverter.utils.getCoreExtension
import com.bojan.gamelistconverter.utils.getExecutableExtension
import com.bojan.gamelistmanager.commandexecutor.domain.config.ExecConfiguration
import java.io.File

/**
 * UseCase that executes command with arguments.
 *
 * @param executor Instance of [CommandExecutor] that will execute the command.
 */
class ExecuteCommandUseCase(private val executor: CommandExecutor) {

    /**
     * Execute provided command.
     *
     * @param execConfig Configuration used for executing command.
     */
    operator fun invoke(execConfig: ExecConfiguration) {

        when (execConfig) {
            is ExecConfiguration.RunRom -> {
                val retroArchDir = execConfig.retroArchDir
                val separator = File.separator
                val exePath = "${retroArchDir}${separator}$RETRO_ARCH_EXECUTABLE.${getExecutableExtension()}"
                val param = LIBERTO_FILE_ARGUMENT
                val corePath = "${retroArchDir}${separator}$CORES_DIR${separator}${execConfig.coreFileName}.${getCoreExtension()}"
                val gamePath = execConfig.romPath
                executor.executeCommand(listOf(exePath, param, corePath, gamePath))
            }
        }
    }

    companion object {
        const val LIBERTO_FILE_ARGUMENT = "-L"
        const val RETRO_ARCH_EXECUTABLE = "retroarch"
        const val CORES_DIR = "cores"
    }
}