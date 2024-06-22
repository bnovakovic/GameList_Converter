package com.bojan.gamelistmanager.gamelistprovider.repository.converter.jsonConverter.serializable

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SystemInfoObject(
    @SerialName("System")
    val system: String,
    val software: String,
    val database: String,
    val web: String
) {
    companion object {
        val unknown = SystemInfoObject("", "", "", "")
    }
}