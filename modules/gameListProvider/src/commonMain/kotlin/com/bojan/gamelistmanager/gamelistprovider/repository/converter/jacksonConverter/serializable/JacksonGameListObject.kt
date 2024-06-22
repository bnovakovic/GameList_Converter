package com.bojan.gamelistmanager.gamelistprovider.repository.converter.jacksonConverter.serializable

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "gameList")
data class JacksonGameListObject(
    @JacksonXmlProperty(localName = "provider")
    val provider: JacksonSystemInfoObject?,
    @JacksonXmlProperty(localName = "game")
    @JacksonXmlElementWrapper(useWrapping = false)
    val game: List<JacksonGameObject>
)