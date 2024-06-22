package com.bojan.gamelistmanager.retroarchinfoloader.repository

import com.bojan.gamelistmanager.retroarchinfoloader.domain.data.RetroArchInfoData
import com.bojan.gamelistmanager.retroarchinfoloader.domain.interfaces.RetroArchInfoReader
import java.io.File
import java.util.Properties

/**
 * Repository implementation of the [RetroArchInfoReader].
 */
class RetroArchInfoReaderAsProperties : RetroArchInfoReader {
    override suspend fun readInfo(fileList: List<File>): List<RetroArchInfoData> {
        val infoList = mutableListOf<RetroArchInfoData>()
        fileList.forEach { infoFile ->
            if (IGNORED_INFO_FILES.contains(infoFile.name)) {
                return@forEach
            }
            val properties = Properties()
            infoFile.inputStream().use { reader ->
                try {
                    properties.load(reader)
                    val info = RetroArchInfoData(
                        filename = infoFile.name.removeSuffix(".${infoFile.extension}"),
                        displayName = properties.getProperty("display_name", "").removeQuotes(),
                        categories = properties.getProperty("categories", "").removeQuotes(),
                        authors = properties.getProperty("authors", "").removeQuotes(),
                        coreName = properties.getProperty("corename", "").removeQuotes(),
                        supportedExtensions = properties.getProperty("supported_extensions", "").removeQuotes(),
                        permissions = properties.getProperty("permissions", "").removeQuotes(),
                        license = properties.getProperty("license", "").removeQuotes(),
                        version = properties.getProperty("display_version", "").removeQuotes(),
                        database = properties.getProperty("database", "").removeQuotes(),
                        manufacturer = properties.getProperty("manufacturer", "").removeQuotes(),
                        systemName = properties.getProperty("systemname", "").removeQuotes(),
                        systemId = properties.getProperty("systemid", "").removeQuotes(),
                        notes = properties.getProperty("notes", "").removeQuotes(),
                        description = properties.getProperty("description", "").removeQuotes(),
                        cheats = properties.getProperty("cheats", "").removeQuotes(),
                        saveStates = properties.getProperty("savestate", "").removeQuotes(),
                    )
                    infoList.add(info)
                } catch (e: IllegalArgumentException) {
                    println("Error loading file $infoFile")
                }
            }
        }
        return infoList
    }

    companion object {
        val IGNORED_INFO_FILES = listOf("00_example_libretro.info", "open-source-notices.info")
    }

    private fun String.removeQuotes() = this.removeSurrounding("\"")
}