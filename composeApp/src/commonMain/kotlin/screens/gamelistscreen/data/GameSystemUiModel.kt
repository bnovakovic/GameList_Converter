package screens.gamelistscreen.data

import com.bojan.gamelistmanager.gamelistprovider.domain.data.RetroArchCoreInfo
import commonui.textlist.SelectableItemUiModel
import java.io.File

/**
 * Model for the game system (Atari2600, NES, Genesis, MAME... etc).
 */
data class GameSystemUiModel(
    val system: SystemUiModel,
    val games: List<GameInfoUiModel>,
    val path: File,
) : SelectableItemUiModel(
    text = "${system.name} (${system.systemPath.name})",
    disabledOrHiddenItem = false
) {
    companion object {
        val empty = GameSystemUiModel(SystemUiModel("", File(""), RetroArchCoreInfo.empty), emptyList(), File(""))
    }
}