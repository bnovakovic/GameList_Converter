package com.bojan.gamelistmanager.retroarchinfoloader.domain

import com.bojan.gamelistmanager.retroarchinfoloader.domain.data.RetroArchInfoData
import com.bojan.gamelistmanager.retroarchinfoloader.domain.interfaces.RetroArchInfoDataRepository
import com.bojan.gamelistmanager.retroarchinfoloader.domain.interfaces.RetroArchInfoReader
import java.io.File

/**
 * UseCase used to load RetroArch information and populate dataSource with converted information.
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
        val config: Map<String, String> = getRetroarchSettings(retroArchDir)
        dataSource.loadRetroArchCfg(config)
        val infoDir = getInfoDir(retroArchDir, config)
        if (infoDir.exists()) {
            val fileList = infoDir.listFiles { file -> file.isFile && file.extension == INFO_FILE_EXTENSION }
            fileList?.let {
                dataSource.loadInfoList(reader.readInfo(it.toList()))
            }

        }
    }

    private fun getRetroarchSettings(retroArchDir: File): Map<String, String> {
        val configMap = mutableMapOf<String, String>()
        val cfgFile = File(retroArchDir, RETROARCH_CFG_FILE)

        if (cfgFile.exists()) {
            cfgFile.forEachLine { line ->
                val trimmed = line.trim()
                if (trimmed.contains("=")) {
                    val (key, rawValue) = trimmed.split(SETTINGS_SPLIT, limit = 2)
                    val value = rawValue.trim().removeSurrounding("\"")
                    configMap[key.trim()] = value
                }
            }
        }
        return configMap
    }

    private fun getInfoDir(retroArchDir: File, settings: Map<String, String>): File {
        val infoFilesDirFromSettings = settings[INFO_PATH_SETTINGS_KEY]
        if (infoFilesDirFromSettings != null) {
            if (infoFilesDirFromSettings.startsWith(SETTINGS_LOCAL_PATH_PREFIX)) {
                val subDir = infoFilesDirFromSettings.substringAfter(SETTINGS_LOCAL_PATH_PREFIX)
                return File(retroArchDir, subDir)
            } else {
                return File(infoFilesDirFromSettings)
            }
        }

        return File(retroArchDir, INFO_SUBDIRECTORY)
    }

    companion object {
        const val RETROARCH_CFG_FILE = "retroarch.cfg"
        const val INFO_FILE_EXTENSION = "info"
        const val INFO_SUBDIRECTORY = "info"
        const val INFO_PATH_SETTINGS_KEY = "libretro_info_path"
        const val SETTINGS_LOCAL_PATH_PREFIX = ":\\"
        const val SETTINGS_SPLIT = "="
    }
}