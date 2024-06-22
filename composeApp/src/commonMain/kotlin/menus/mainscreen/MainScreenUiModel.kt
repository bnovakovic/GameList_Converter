package menus.mainscreen

import app.ActiveScreen
import app.Dialogues

/**
 * UI model representing state of the main screen.
 *
 * @param activeScreen Current active screen.
 * @param dialogue Current active Dialogue.
 * @param inDarkMode True if we are in dark mode, false if we are not.
 * @param workInProgress True if we are working on something in the background, false if we do not.
 */
data class MainScreenUiModel(
    val activeScreen: ActiveScreen = ActiveScreen.GAME_LIST_SCREEN,
    val dialogue: Dialogues = Dialogues.NONE,
    val inDarkMode: Boolean = false,
    val workInProgress: Boolean = false
)