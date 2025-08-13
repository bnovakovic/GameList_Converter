package com.bojan.gamelistmanager.retroarchinfoloader.repository

import com.bojan.gamelistmanager.retroarchinfoloader.domain.data.RetroArchInfoData
import com.bojan.gamelistmanager.retroarchinfoloader.domain.interfaces.RetroArchInfoDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repository information of the [RetroArchInfoDataRepository].
 */
object RetroArchInfoDataSource : RetroArchInfoDataRepository {

    private val _infoList = MutableStateFlow<List<RetroArchInfoData>>(emptyList())

    override val infoList = _infoList.asStateFlow()

    private val _settings = MutableStateFlow<Map<String, String>>(emptyMap())

    override val settings = _settings.asStateFlow()

    override fun loadInfoList(infoList: List<RetroArchInfoData>) {
        _infoList.value = infoList
    }

    override fun loadRetroArchCfg(settings: Map<String, String>) {
        _settings.value = settings
    }

    override fun clearData() {
        _infoList.value = emptyList()
    }
}