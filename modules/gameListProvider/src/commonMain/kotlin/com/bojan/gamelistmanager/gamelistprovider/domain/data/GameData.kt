package com.bojan.gamelistmanager.gamelistprovider.domain.data

import java.io.File

/**
 * Data class containing information about the game.
 *
 */
data class GameData(
    val genreId: Int,
    val image: File?,
    val video: File?,
    val thumbnail: File?,
    val players: String?,
    val rating: Float,
    val source: String?,
    val releaseDate: String?,
    val path: File,
    val name: String,
    val genre: String?,
    val publisher: String?,
    val developer: String?,
    val id: Int,
    val hash: String,
    val desc: String?,
    val hidden: Boolean,
    val adult: Boolean,
    val kidGame: Boolean,
    val region: String?,
    val favorite: Boolean,
)