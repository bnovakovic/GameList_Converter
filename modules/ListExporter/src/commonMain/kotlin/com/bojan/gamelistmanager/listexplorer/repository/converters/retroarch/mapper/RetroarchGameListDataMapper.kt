package com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch.mapper

import com.bojan.gamelistmanager.listexplorer.domain.GameListConvertConfig
import com.bojan.gamelistmanager.listexplorer.domain.data.ExportGameData
import com.bojan.gamelistmanager.listexplorer.domain.data.ExportGameListData
import com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch.data.RetroArchGameItem
import com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch.data.RetroArchListObject
import java.io.File

fun ExportGameListData.toRetroArchListObject(config: GameListConvertConfig) : RetroArchListObject {
    return RetroArchListObject(
        version = config.playlistVersion,
        defaultCorePath = config.corePath?.toString() ?: AUTO_DETECT_VALUE,
        defaultCoreName = config.coreName ?: AUTO_DETECT_VALUE,
        labelDisplayMode = 0,
        rightThumbnailMode = 0,
        leftThumbnailMode = 0,
        thumbnailMatchMode = 0,
        sortMode = 0,
        scanContentDir = this.originalPath,
        scanFileExts = "",
        scanDatFilePath = "",
        scanSearchRecursively = true,
        scanSearchArchives = false,
        scanFilterDatContent = false,
        scanOverwritePlaylist = false,
        items = this.games
            .filter {  gameData -> (!gameData.hidden || config.addHidden) && !gameData.name.contains(NOT_GAME_STRING)  }
            .map { gameData ->
            gameData.toRetroArchGameItem(this.originalPath, gameData.path, config.outputFileName)
        }
    )
}

fun ExportGameData.toRetroArchGameItem(originalPath: String, subPath: String, filename: String): RetroArchGameItem {
    val formattedPath: String = if (subPath.startsWith(UNWANTED_PATH_START)) subPath.substring(1) else subPath
    return RetroArchGameItem(
        path = File(originalPath, formattedPath).toString(),
        label = this.name,
        corePath = AUTO_DETECT_VALUE,
        coreName = AUTO_DETECT_VALUE,
        crc32 = "00000000|crc",
        dbName = filename
    )
}

const val AUTO_DETECT_VALUE = "DETECT"
const val UNWANTED_PATH_START = ".\\"
const val NOT_GAME_STRING = "ZZZ(notgame)"