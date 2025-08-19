package com.bojan.gamelistmanager.commandexecutor.repository

import com.bojan.gamelistmanager.commandexecutor.domain.CommandExecutor
import com.bojan.gamelistmanager.commandexecutor.domain.CommandResult

/**
 * [ProcessBuilder] instance of [CommandExecutor].
 */
class ProcessBuilderExecutor : CommandExecutor {

    override suspend fun executeCommand(args: List<String>): CommandResult {
        println("Executing command $args")
        val processBuilder = ProcessBuilder(args)
        val process = processBuilder.start()
        val output = process.inputStream.bufferedReader().readText()
        val exitCode = process.waitFor()
        return CommandResult(exitCode, output.trim())
    }
}