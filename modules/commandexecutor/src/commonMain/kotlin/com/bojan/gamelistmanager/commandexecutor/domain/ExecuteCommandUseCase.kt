package com.bojan.gamelistmanager.commandexecutor.domain

import com.bojan.gamelistmanager.commandexecutor.domain.config.ExecConfiguration

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
                val exePath = execConfig.retroArchExecutablePath
                val param = LIBERTO_FILE_ARGUMENT
                val corePath = execConfig.coreFullPath
                val gamePath = execConfig.romPath
                return executor.executeCommand(listOf(exePath, param, corePath, gamePath))
            }
        }
    }

    companion object {
        const val LIBERTO_FILE_ARGUMENT = "-L"
    }
}