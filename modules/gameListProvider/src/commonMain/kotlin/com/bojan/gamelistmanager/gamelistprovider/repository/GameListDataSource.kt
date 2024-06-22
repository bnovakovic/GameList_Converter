package com.bojan.gamelistmanager.gamelistprovider.repository

import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import com.bojan.gamelistmanager.gamelistprovider.domain.interfaces.GameListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/***
 * Singleton implementation of [GameListRepository].
 */
object GameListDataSource : GameListRepository {
    private val _gameListContainer = MutableStateFlow(emptyList<GameListData>())
    override val gameList = _gameListContainer.asStateFlow()

    override fun loadGames(games: List<GameListData>) {
        _gameListContainer.value = games
    }

    override fun clearData() {
        _gameListContainer.value = emptyList()
    }
}