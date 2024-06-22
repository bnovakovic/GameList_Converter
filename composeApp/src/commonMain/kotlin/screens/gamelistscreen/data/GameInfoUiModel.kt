package screens.gamelistscreen.data

import commonui.textlist.SelectableItemUiModel
import screens.gamelistscreen.mappers.Availability
import java.io.File

/**
 * Model representing game information.
 */
data class GameInfoUiModel(
    val gameName: String,
    val imagePath: File?,
    val videoPath: File?,
    val region: String,
    val date: String,
    val players: String,
    val rating: String,
    val kidGame: Availability,
    val hidden: Availability,
    val favorite: Availability,
    val publisher: String,
    val developer: String,
    val genre: String,
    val description: String,
    val romPath: String
) : SelectableItemUiModel(gameName, hidden == Availability.YES)