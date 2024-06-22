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
     * Loads the provided list of core info items into the repository.
     * @param infoList The list of core information to be loaded.
     */
    fun loadInfoList(infoList: List<RetroArchInfoData>)

    /**
     * Clears the data stored in the repository.
     */
    fun clearData()
}