package com.bojan.gamelistmanager.gamelistprovider.domain.interfaces

import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import java.io.File

/**
 * Repository interface used to convert Gamelist.xml in to the [GameListData].
 */
interface XmlConverter {
    /**
     * Converts Gamelist.xml in to [GameListData].
     * @param xmlPath path to the XML file.
     *
     * @return Converted XML to [GameListData]
     */
    suspend fun convertXmlToGameListItem(xmlPath: File) : GameListData
}