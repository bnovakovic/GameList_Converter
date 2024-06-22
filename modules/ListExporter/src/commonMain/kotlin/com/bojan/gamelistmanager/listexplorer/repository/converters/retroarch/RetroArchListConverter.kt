package com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch

import com.bojan.gamelistmanager.listexplorer.domain.GameListConvertConfig
import com.bojan.gamelistmanager.listexplorer.domain.GameListConverter
import com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch.mapper.toRetroArchListObject
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Repository implementation of the [GameListConverter].
 */
class RetroArchListConverter : GameListConverter {
    override suspend fun convertList(exportConfig: GameListConvertConfig) {
        val outputDir = exportConfig.outputDir
        val outputFileName = exportConfig.outputFileName
        val gameListData = exportConfig.gameListData

        outputDir.mkdirs()
        val fullFile = File(outputDir, outputFileName)
        val converted = gameListData.toRetroArchListObject(exportConfig)
        val json = Json { prettyPrint = true }
        val jsonString = json.encodeToString(converted)
        fullFile.writeText(jsonString)
    }
}