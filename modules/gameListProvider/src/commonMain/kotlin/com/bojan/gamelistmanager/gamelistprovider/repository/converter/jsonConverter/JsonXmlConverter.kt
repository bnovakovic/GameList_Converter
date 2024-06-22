package com.bojan.gamelistmanager.gamelistprovider.repository.converter.jsonConverter

import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import com.bojan.gamelistmanager.gamelistprovider.domain.interfaces.XmlConverter
import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jsonConverter.mapper.toGameListData
import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jsonConverter.serializable.GameListContainer
import kotlinx.serialization.json.Json
import org.json.XML
import java.io.File

/**
 * JSON implementation of XML converter.
 */
class JsonXmlConverter : XmlConverter {
    override suspend fun convertXmlToGameListItem(xmlPath: File) : GameListData {
        val jsonObject = XML.toJSONObject(xmlPath.readText(), true)
        val json = Json { ignoreUnknownKeys = true }
        val converted = json.decodeFromString<GameListContainer>(jsonObject.toString())
        return converted.toGameListData(xmlPath.parentFile)
    }
}