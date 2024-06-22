package screens.gamelistscreen.data

import com.bojan.gamelistmanager.gamelistprovider.domain.data.RetroArchCoreInfo
import java.io.File

data class SystemUiModel (
    val name: String,
    val systemPath: File,
    val retroArchCoreInfo: RetroArchCoreInfo
)