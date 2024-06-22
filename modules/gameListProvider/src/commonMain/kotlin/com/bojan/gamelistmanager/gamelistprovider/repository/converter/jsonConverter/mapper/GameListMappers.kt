package com.bojan.gamelistmanager.gamelistprovider.repository.converter.jsonConverter.mapper

import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameData
import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import com.bojan.gamelistmanager.gamelistprovider.domain.data.SystemData
import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jsonConverter.serializable.GameListContainer
import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jsonConverter.serializable.GameObject
import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jsonConverter.serializable.SystemInfoObject
import com.bojan.gamelistmanager.gamelistprovider.repository.mapper.gameListDirToDatabase
import com.bojan.gamelistmanager.gamelistprovider.repository.mapper.gameListDirToSystemName
import java.io.File

fun SystemInfoObject.toSystemData(path: File) = SystemData(
    name = gameListDirToSystemName(path.name),
    software = this.software,
    database = this.database,
    web = this.web,
    retroArchCoreInfo = gameListDirToDatabase(path.name)
)

fun GameObject.toGameData() = GameData(
    genreId = this.genreid?.toIntOrNull() ?: -1,
    image = this.image?.let { File(it) },
    video = this.video?.let { File(it) },
    thumbnail = this.thumbnail?.let { File(it) },
    players = this.players,
    rating = this.rating?.toFloatOrNull() ?: 0.0f,
    source = this.source,
    releaseDate = this.releasedate,
    path = File(this.path),
    name = this.name,
    genre = this.genre,
    publisher = this.publisher,
    developer = this.developer,
    id = this.id?.toInt() ?: -1,
    hash = this.hash ?: "",
    desc = this.desc,
    hidden = this.hidden?.toBooleanStrictOrNull() ?: false,
    adult = this.adult?.toBooleanStrictOrNull() ?: false,
    kidGame = this.kidgame?.toBooleanStrictOrNull() ?: false,
    region = this.region,
    favorite = this.favorite?.toBooleanStrictOrNull() ?: false,
)

fun GameListContainer.toGameListData(gameListPath: File) = GameListData(
    games = this.gameList.game.map { it.toGameData() },
    system = this.gameList.systemInfo.toSystemData(gameListPath),
    originalPath = gameListPath
)
