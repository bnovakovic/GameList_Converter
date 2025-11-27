package com.bojan.gamelistmanager.gamelistprovider.repository.converter.jacksonConverter.mapper

import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameData
import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import com.bojan.gamelistmanager.gamelistprovider.domain.data.SystemData
import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jacksonConverter.serializable.JacksonGameListObject
import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jacksonConverter.serializable.JacksonGameObject
import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jacksonConverter.serializable.JacksonSystemInfoObject
import com.bojan.gamelistmanager.gamelistprovider.repository.mapper.gameListDirToDatabase
import com.bojan.gamelistmanager.gamelistprovider.repository.mapper.gameListDirToSystemName
import java.io.File

fun JacksonSystemInfoObject.toSystemData(path: File) = SystemData(
    name = gameListDirToSystemName(path.name),
    software = this.software ?: "",
    database = this.database ?: "",
    systemSubDir = path.name,
    web = this.web ?: "",
    retroArchCoreInfo = gameListDirToDatabase(path.name)
)

fun JacksonGameObject.toGameData() = GameData(
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
    id = this.id?.toIntOrNull() ?: -1,
    hash = this.hash ?: "",
    desc = this.desc,
    hidden = this.hidden?.toBooleanStrictOrNull() ?: false,
    adult = this.adult?.toBooleanStrictOrNull() ?: false,
    kidGame = this.kidgame?.toBooleanStrictOrNull() ?: false,
    region = this.region,
    favorite = this.favorite?.toBooleanStrictOrNull() ?: false,
)

fun JacksonGameListObject.toGameListData(gameListPath: File) = GameListData(
    games = this.game.map { it.toGameData() },
    system = this.provider?.toSystemData(gameListPath) ?: SystemData(
        name = gameListDirToSystemName(gameListPath.name),
        software = "",
        database = "",
        systemSubDir = gameListPath.name,
        web = "",
        retroArchCoreInfo = gameListDirToDatabase(gameListPath.name)
    ),
    originalPath = gameListPath
)
