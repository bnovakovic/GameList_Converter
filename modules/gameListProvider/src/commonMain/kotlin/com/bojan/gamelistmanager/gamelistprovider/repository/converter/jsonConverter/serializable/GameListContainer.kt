package com.bojan.gamelistmanager.gamelistprovider.repository.converter.jsonConverter.serializable

import kotlinx.serialization.Serializable

@Serializable
data class GameListContainer(val gameList: GameListObject)