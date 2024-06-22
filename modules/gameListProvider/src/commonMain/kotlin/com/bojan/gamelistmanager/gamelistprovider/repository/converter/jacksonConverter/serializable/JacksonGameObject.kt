package com.bojan.gamelistmanager.gamelistprovider.repository.converter.jacksonConverter.serializable

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class JacksonGameObject(
    @JacksonXmlProperty(localName = "genreid")
    val genreid: String? = null,
    @JacksonXmlProperty(localName = "image")
    val image: String? = null,
    @JacksonXmlProperty(localName = "video")
    val video: String? = null,
    @JacksonXmlProperty(localName = "thumbnail")
    val thumbnail: String? = null,
    @JacksonXmlProperty(localName = "players")
    val players: String? = null,
    @JacksonXmlProperty(localName = "rating")
    val rating: String? = null,
    @JacksonXmlProperty(localName = "source")
    val source: String? = null,
    @JacksonXmlProperty(localName = "releasedate")
    val releasedate: String? = null,
    @JacksonXmlProperty(localName = "path")
    val path: String,
    @JacksonXmlProperty(localName = "name")
    val name: String,
    @JacksonXmlProperty(localName = "genre")
    val genre: String? = null,
    @JacksonXmlProperty(localName = "publisher")
    val publisher: String? = null,
    @JacksonXmlProperty(localName = "developer")
    val developer: String? = null,
    @JacksonXmlProperty(localName = "id")
    val id: String? = null,
    @JacksonXmlProperty(localName = "hash")
    val hash: String? = null,
    @JacksonXmlProperty(localName = "desc")
    val desc: String? = null,
    @JacksonXmlProperty(localName = "hidden")
    val hidden: String? = null,
    @JacksonXmlProperty(localName = "adult")
    val adult: String? = null,
    @JacksonXmlProperty(localName = "kidgame")
    val kidgame: String? = null,
    @JacksonXmlProperty(localName = "region")
    val region: String? = null,
    @JacksonXmlProperty(localName = "favorite")
    val favorite: String? = null,
)