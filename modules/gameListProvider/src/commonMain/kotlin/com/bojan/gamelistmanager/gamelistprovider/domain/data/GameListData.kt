package com.bojan.gamelistmanager.gamelistprovider.domain.data

import java.io.File

/**
 * Data class containing full Game list data which includes list of game, system info, file path... etc.
 *
 * @param games List of [GameData] objects containing game information.
 * @param system [SystemData] containing system information that runs these games.
 * @param originalPath Path to the gamelist.xml file.
 */
data class GameListData(val games: List<GameData>, val system: SystemData, val originalPath: File)