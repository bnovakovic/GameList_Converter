package com.bojan.gamelistmanager.gamelistprovider.domain.interfaces

import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import kotlinx.coroutines.flow.StateFlow

/**
 * Repository interface for managing list of game containers. Contains list of [GameListData] and methods to manage it.
 */
interface GameListRepository {
    /**
     * A [StateFlow] containing list of games.
     */
    val gameList: StateFlow<List<GameListData>>

    /**
     * Loads the provided list of games into the repository.
     * @param games The list of games to be loaded.
     */
    fun loadGames(games: List<GameListData>)

    /**
     * Clears the data stored in the repository.
     */
    fun clearData()
}