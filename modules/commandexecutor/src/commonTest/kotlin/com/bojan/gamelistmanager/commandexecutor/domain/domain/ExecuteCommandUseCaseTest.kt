package com.bojan.gamelistmanager.commandexecutor.domain.domain

import com.bojan.gamelistconverter.utils.getExecutableExtension
import com.bojan.gamelistmanager.commandexecutor.domain.CommandExecutor
import com.bojan.gamelistmanager.commandexecutor.domain.CommandResult
import com.bojan.gamelistmanager.commandexecutor.domain.ExecuteCommandUseCase
import com.bojan.gamelistmanager.commandexecutor.domain.config.ExecConfiguration
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ExecuteCommandUseCaseTest {
    @Test
    fun `test retroarch path generated properly when executable is in path`() = runBlocking {
        val executableExtension = getExecutableExtension()
        val tested = ExecuteCommandUseCase(createFakeExecutor(0, "user/home/retroarch.$executableExtension"))
        val result = tested.invoke(ExecConfiguration.FindRetroArchPath)
        assertEquals(CommandResult(0, "user/home/retroarch.$executableExtension"), result)
    }

    @Test
    fun `test executable added when only directory is in path`() = runBlocking {
        val tested = ExecuteCommandUseCase(createFakeExecutor(0, "user/home/retroarch/"))
        val result = tested.invoke(ExecConfiguration.FindRetroArchPath)
        assertEquals(CommandResult(0, "user/home/retroarch/"), result)
    }

    @Test
    fun `test wrong function does not append executable`() = runBlocking {
        val tested = ExecuteCommandUseCase(createFakeExecutor(2, "user/home/retroarch/"))
        val result = tested.invoke(ExecConfiguration.FindRetroArchPath)
        assertEquals(CommandResult(2, "user/home/retroarch/"), result)
    }


    private fun createFakeExecutor(code: Int, output: String): CommandExecutor {
        return object : CommandExecutor {
            override suspend fun executeCommand(args: List<String>): CommandResult {
                return CommandResult(code, output)
            }
        }
    }
}