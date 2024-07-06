package com.bojan.gamelistmanager.commandexecutor.repository

import com.bojan.gamelistmanager.commandexecutor.domain.CommandExecutor

/**
 * [ProcessBuilder] instance of [CommandExecutor].
 */
class ProcessBuilderExecutor : CommandExecutor {

    override suspend fun executeCommand(args: List<String>): Int {
        println("Executing command $args")
        val processBuilder = ProcessBuilder(args)
        return processBuilder.start().waitFor()
    }
}