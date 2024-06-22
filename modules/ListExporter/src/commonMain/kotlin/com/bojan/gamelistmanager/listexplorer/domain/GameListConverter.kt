package com.bojan.gamelistmanager.listexplorer.domain

/**
 * Repository interface used to convert used to convert list of games to RetroArch playlist.
 */
interface GameListConverter {

    /**
     * Converts the provided information in the [GameListConvertConfig] to RetroArch playlist and saves it to the file.
     *
     * @param exportConfig A [GameListConvertConfig] containing crucial information about the list that needs to be created.
     */
    suspend fun convertList(exportConfig: GameListConvertConfig)
}