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
     * Execute provided command. Causes the current thread to wait, if necessary, until the process represented by this Process object has
     * terminated. This method returns immediately if the process has already terminated. If the process has not yet terminated,
     * the calling thread will be blocked until the process exits.
     *
     * @param execConfig Configuration used for executing command.
     */
    suspend operator fun invoke(execConfig: ExecConfiguration): Int {
        when (execConfig) {
            is ExecConfiguration.RunRom -> {
                val retroArchDir = execConfig.retroArchDir
                val separator = File.separator
                val exePath = "${retroArchDir}${separator}$RETRO_ARCH_EXECUTABLE.${getExecutableExtension()}"
                val param = LIBERTO_FILE_ARGUMENT
                val gamePath = execConfig.romPath
                return executor.executeCommand(listOf(exePath, param, execConfig.corePath, gamePath))
            }
        }
    }

    companion object {
        const val LIBERTO_FILE_ARGUMENT = "-L"
        const val RETRO_ARCH_EXECUTABLE = "retroarch"
    }
}