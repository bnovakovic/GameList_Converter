package com.bojan.gamelistmanager.listexplorer.domain

import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import java.io.File

/**
 * Configuration containing crucial information about the information for the RetroArch list to be created.
 *
 * @param outputDir Output directory where we want to save the list. Usually this is %RetroArchDir%/playlists.
 * @param outputFileName FileName of the list. RetroArch identifies system by the filename, so name should be proper for the System.
 * @param gameListData A [GameListData] containing all needed information about the system and the games.
 * @param coreName Name of the core to use. If null, 'DETECT' will be used and RetroArch will detect it manually.
 * @param corePath Path to the core file.
 * @param addHidden If true hidden games will be added, otherwise hidden games will be skipped.
 * @param playlistVersion Version of the RetroArch playlist.
 */
data class GameListConvertConfig(
    val outputDir: File,
    val outputFileName: String,
    val gameListData: GameListData,
    val coreName: String?,
    val corePath: File?,
    val addHidden: Boolean,
    val playlistVersion: String
)