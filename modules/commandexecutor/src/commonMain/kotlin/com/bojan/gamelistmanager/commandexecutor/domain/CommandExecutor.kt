package com.bojan.gamelistmanager.commandexecutor.domain

/**
 * Interface representing command executor that will be used to execute command line with arguments.
 */
interface CommandExecutor {

    /**
     * Executes command.
     *
     * @param args List of arguments for the command.
     * @return Exit value of the process. 0 means normal termination.
     */
    suspend fun executeCommand(args: List<String>) : Int
}