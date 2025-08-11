package com.bojan.gamelistmanager.gamelistprovider.repository.converter.jacksonConverter

import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import com.bojan.gamelistmanager.gamelistprovider.domain.interfaces.XmlConverter
import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jacksonConverter.mapper.toGameListData
import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jacksonConverter.serializable.JacksonGameListObject
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import java.io.File

/**
 * Jackson Implementation of [XmlConverter].
 */
class JacksonXmlConverter : XmlConverter {
    override suspend fun convertXmlToGameListItem(xmlPath: File): GameListData {
        val stringToParse = xmlPath.readText()

        val mapper = XmlMapper()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        val converted: JacksonGameListObject = mapper.readValue(stringToParse, JacksonGameListObject::class.java)

        return converted.toGameListData(xmlPath.parentFile)
    }
}