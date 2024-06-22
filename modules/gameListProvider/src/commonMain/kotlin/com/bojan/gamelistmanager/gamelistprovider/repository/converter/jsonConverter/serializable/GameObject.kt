package com.bojan.gamelistmanager.gamelistprovider.repository.converter.jsonConverter.serializable

import kotlinx.serialization.Serializable

@Serializable
data class GameObject(
    val genreid: String? = null,
    val image: String? = null,
    val video: String? = null,
    val thumbnail: String? = null,
    val players: String? = null,
    val rating: String? = null,
    val source: String? = null,
    val releasedate: String? = null,
    val path: String,
    val name: String,
    val genre: String? = null,
    val publisher: String? = null,
    val developer: String? = null,
    val id: String? = null,
    val hash: String? = null,
    val desc: String? = null,
    val hidden: String? = null,
    val adult: String? = null,
    val kidgame: String? = null,
    val region: String? = null,
    val favorite: String? = null,
)