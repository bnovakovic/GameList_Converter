package screens.gamelistscreen.data

import com.bojan.gamelistmanager.gamelistprovider.domain.data.RetroArchCoreInfo
import java.io.File

/**
 * UI Model for the System.
 */
data class SystemUiModel (
    val name: String,
    val systemPath: File,
    val retroArchCoreInfo: RetroArchCoreInfo
)