package screens.export.retroarch

import screens.export.PlaylistSaveProgress
import screens.gamelistscreen.data.CoreInfoUiModel
import screens.gamelistscreen.mappers.Availability
import java.io.File

/**
 * UI model for the Export screen.
 */
data class ExportRetroArchScreenUiModel(
    val selectedSystem: Int = 0,
    val selectedCore: Int = 0,
    val coreInfo: CoreInfoUiModel = CoreInfoUiModel(
        filename = "",
        displayName = "",
        categories = "",
        authors = "",
        coreName = "",
        supportedExtensions = "",
        license = "",
        permissions = "",
        version = "",
        database = "",
        manufacturer = "",
        systemName = "",
        systemId = "",
        notes = "",
        description = "",
        cheats = Availability.UNKNOWN,
        saveStates = Availability.UNKNOWN,
        compatibleWithActiveSystem = true
    ),
    val exportAllowed: Boolean = true,
    val exportPath: File = File(""),
    val playlistOptions: List<String> = listOf(""),
    val selectedPlayListOption: Int = 0,
    val saveFileName: String = "",
    val coreMissing: Boolean = false,
    val confirmCoreMissing: Boolean = false,
    val showAllCores: Boolean = false,
    val saveFileResult: PlaylistSaveProgress = PlaylistSaveProgress.NONE,
    val numberOfGames: Int = 0,
)