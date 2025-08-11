package com.bojan.gamelistmanager.gamelistprovider.repository.converter.jacksonConverter.serializable

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import kotlinx.serialization.Serializable

@Serializable
data class JacksonSystemInfoObject(
    @JacksonXmlProperty(localName = "System")
    val system: String?,
    @JacksonXmlProperty(localName = "software")
    val software: String?,
    @JacksonXmlProperty(localName = "database")
    val database: String?,
    @JacksonXmlProperty(localName = "web")
    val web: String?
)