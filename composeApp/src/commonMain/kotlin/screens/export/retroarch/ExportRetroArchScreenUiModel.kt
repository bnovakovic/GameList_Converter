package screens.export.retroarch

import screens.export.PlaylistSaveProgress
import screens.gamelistscreen.data.CoreInfoUiModel
import screens.gamelistscreen.data.GameSystemUiModel
import screens.gamelistscreen.data.SystemUiModel
import screens.gamelistscreen.mappers.Availability
import java.io.File

/**
 * UI model for the Export screen.
 */
data class ExportRetroArchScreenUiModel(
    val selectedSystem: Int = 0,
    val selectedCore: Int = 0,
    val coreInfo: CoreInfoUiModel = CoreInfoUiModel.none,
    val systemInfo: GameSystemUiModel = GameSystemUiModel.empty,
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
    val isRunAvailable: Boolean = false,
    val executeResult: Int = execResultNone
) {
    companion object {
        const val execResultNone = -1
    }
}