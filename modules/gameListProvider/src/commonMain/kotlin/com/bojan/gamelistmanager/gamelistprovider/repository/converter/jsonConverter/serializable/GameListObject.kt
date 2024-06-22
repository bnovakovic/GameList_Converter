package com.bojan.gamelistmanager.gamelistprovider.repository.converter.jsonConverter.serializable

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameListObject(
    val game: List<GameObject> = emptyList(),
    @SerialName("provider")
    val systemInfo: SystemInfoObject = SystemInfoObject.unknown
)