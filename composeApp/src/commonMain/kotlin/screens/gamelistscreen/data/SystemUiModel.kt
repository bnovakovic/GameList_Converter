package screens.gamelistscreen.data

import com.bojan.gamelistmanager.gamelistprovider.domain.data.RetroArchCoreInfo

/**
 * UI Model for the System.
 */
data class SystemUiModel (
    val name: String,
    val subdir: String,
    val retroArchCoreInfo: RetroArchCoreInfo
)