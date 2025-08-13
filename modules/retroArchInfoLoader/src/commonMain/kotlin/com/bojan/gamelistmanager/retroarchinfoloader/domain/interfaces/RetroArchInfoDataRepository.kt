package com.bojan.gamelistmanager.retroarchinfoloader.domain.interfaces

import com.bojan.gamelistmanager.retroarchinfoloader.domain.data.RetroArchInfoData
import kotlinx.coroutines.flow.StateFlow

/**
 * Repository interface for managing list of RetroArch core information. Contains list of [RetroArchInfoData] and methods to manage it.
 */
interface RetroArchInfoDataRepository {

    /**
     * A [StateFlow] containing list of [RetroArchInfoData].
     */
    val infoList: StateFlow<List<RetroArchInfoData>>

    /**
     * A [StateFlow] containing all retroarch settings from the retroarch.cfg file
     */
    val settings: StateFlow<Map<String, String>>

    /**
     * Loads the provided list of core info items into the repository.
     * @param infoList The list of core information to be loaded.
     */
    fun loadInfoList(infoList: List<RetroArchInfoData>)


    /**
     * Loads retroarch.cfg file and parses all of it's configuration
     * @param settings all retroarch settings.
     */
    fun loadRetroArchCfg(settings: Map<String, String>)

    /**
     * Clears the data stored in the repository.
     */
    fun clearData()
}