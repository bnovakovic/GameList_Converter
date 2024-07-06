package com.bojan.gamelistmanager.commandexecutor.repository

import com.bojan.gamelistmanager.commandexecutor.domain.CommandExecutor

/**
 * [ProcessBuilder] instance of [CommandExecutor].
 */
class ProcessBuilderExecutor : CommandExecutor {

    override fun executeCommand(args: List<String>) {
        println("Executing command $args")
        val processBuilder = ProcessBuilder(args)
        processBuilder.start()
    }
}