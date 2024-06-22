package com.bojan.gamelistmanager.retroarchinfoloader.domain.interfaces

import com.bojan.gamelistmanager.retroarchinfoloader.domain.data.RetroArchInfoData
import java.io.File

/**
 * Repository interface used to convert RetroArch info file to [RetroArchInfoData].
 */
interface RetroArchInfoReader {

    /**
     * Converts list of %corename%.info in to the [RetroArchInfoData]
     * @param fileList list of core information.
     * @return converted list of [RetroArchInfoData]
     */
    suspend fun readInfo(fileList: List<File>): List<RetroArchInfoData>
}