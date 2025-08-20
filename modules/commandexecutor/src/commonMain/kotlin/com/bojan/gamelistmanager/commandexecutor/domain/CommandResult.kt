package com.bojan.gamelistmanager.commandexecutor.domain

/**
 * Result of command execution.
 *
 * @property code command return code.
 * @property output output of the command.
 */
data class CommandResult(val code: Int, val output: String) {
    companion object {
        val InvalidResult = CommandResult(code = 2, output = "")
    }
}