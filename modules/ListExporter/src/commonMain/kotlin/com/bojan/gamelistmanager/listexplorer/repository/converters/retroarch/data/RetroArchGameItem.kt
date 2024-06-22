package com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RetroArchGameItem(
    val path: String,
    val label: String,
    @SerialName("core_path")
    val corePath: String,
    @SerialName("core_name")
    val coreName: String,
    val crc32: String,
    @SerialName("db_name")
    val dbName: String,
)