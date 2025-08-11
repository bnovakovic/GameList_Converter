package com.bojan.gamelistmanager.retroarchinfoloader.domain

import com.bojan.gamelistmanager.retroarchinfoloader.domain.data.RetroArchInfoData
import com.bojan.gamelistmanager.retroarchinfoloader.domain.interfaces.RetroArchInfoDataRepository
import com.bojan.gamelistmanager.retroarchinfoloader.domain.interfaces.RetroArchInfoReader
import java.io.File

/**
 * UseCare used to load RetroArch information and populate dataSource with converted information.
 * @param reader A [RetroArchInfoReader] used to read the information.
 * @param dataSource Data source used to store the converted information.
 */
class LoadRetroArchInfoUseCase(private val reader: RetroArchInfoReader, private val dataSource: RetroArchInfoDataRepository) {

    /**
     * Invokes conversion of the info files to the [RetroArchInfoData].
     * @param retroArchDir Root directory of the RetroArch.
     */
    suspend operator fun invoke(retroArchDir: File) {
        dataSource.clearData()
        val infoDir = findInfoDirectory(retroArchDir)
        if (infoDir.exists()) {
            val fileList = infoDir.listFiles { file -> file.isFile && file.extension == INFO_FILE_EXTENSION }
            fileList?.let {
                dataSource.loadInfoList(reader.readInfo(it.toList()))
            }
        }
    }

    private fun findInfoDirectory(retroArchDir: File): File {
        // List of common relative paths for the 'info' directory
        val potentialInfoPaths = listOf(
            "info",
            "libretro/info",
            "cores/info",
            "assets/info"
        )

        return potentialInfoPaths
            .map { File(retroArchDir, it) }
            .firstOrNull { it.exists() && it.isDirectory }
            ?: File(retroArchDir, INFO_SUBDIRECTORY) // Fallback to the default
    }

    companion object {
        const val INFO_FILE_EXTENSION = "info"
        const val INFO_SUBDIRECTORY = "info"
    }
}