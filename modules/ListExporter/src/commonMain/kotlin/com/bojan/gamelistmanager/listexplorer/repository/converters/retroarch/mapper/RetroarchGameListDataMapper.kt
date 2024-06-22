package com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch.mapper

import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameData
import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import com.bojan.gamelistmanager.listexplorer.domain.GameListConvertConfig
import com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch.data.RetroArchGameItem
import com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch.data.RetroArchListObject
import java.io.File

fun GameListData.toRetroArchListObject(config: GameListConvertConfig) : RetroArchListObject {
    return RetroArchListObject(
        version = config.playlistVersion,
        defaultCorePath = config.corePath?.toString() ?: AUTO_DETECT_VALUE,
        defaultCoreName = config.coreName ?: AUTO_DETECT_VALUE,
        labelDisplayMode = 0,
        rightThumbnailMode = 0,
        leftThumbnailMode = 0,
        thumbnailMatchMode = 0,
        sortMode = 0,
        scanContentDir = this.originalPath.toString(),
        scanFileExts = "",
        scanDatFilePath = "",
        scanSearchRecursively = true,
        scanSearchArchives = false,
        scanFilterDatContent = false,
        scanOverwritePlaylist = false,
        items = this.games
            .filter {  gameData -> !gameData.hidden || config.addHidden }
            .map { gameData ->
            gameData.toRetroArchGameItem(this.originalPath, config.outputFileName)
        }
    )
}

fun GameData.toRetroArchGameItem(originalPath: File, filename: String): RetroArchGameItem {
    return RetroArchGameItem(
        path = File(originalPath, path.name).toString(),
        label = this.name,
        corePath = AUTO_DETECT_VALUE,
        coreName = AUTO_DETECT_VALUE,
        crc32 = "00000000|crc",
        dbName = filename
    )
}

const val AUTO_DETECT_VALUE = "DETECT"