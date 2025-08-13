package com.bojan.gamelistmanager.retroarchinfoloader.domain

import com.bojan.gamelistconverter.utils.getCoreExtension
import com.bojan.gamelistconverter.utils.resolveHomePath
import java.io.File

/**
 * UseCase used to provide full path to the core. Including it's file name and extension.
 */
class ProvideCoreFullPathUseCase {

    /**
     * Provides full path of the core which includes file name and extension.
     *
     * @param retroArchDir RetroArch directory.
     * @param coresPathInSettings core path in settings.
     * @param coreFileName core filename (including extension).
     */
    operator fun invoke(retroArchDir: String, coresPathInSettings: String?, coreFileName: String): File {
        val coreFile = "${coreFileName}.${getCoreExtension()}"
        if (coresPathInSettings != null) {
            if (coresPathInSettings.startsWith(SETTINGS_LOCAL_PATH_PREFIX)) {
                val coresPath = File(retroArchDir, coresPathInSettings.substringAfter(SETTINGS_LOCAL_PATH_PREFIX))
                return File(coresPath, coreFile)
            } else {
                return File(coresPathInSettings.resolveHomePath(), coreFile)
            }
        }
        val coresDir = File(retroArchDir, RETRO_ARCH_CORES_SUBDIR)
        return File(coresDir, coreFile)
    }
}