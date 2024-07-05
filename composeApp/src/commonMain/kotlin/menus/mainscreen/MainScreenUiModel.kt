package menus.mainscreen

import app.ActiveScreen
import app.Dialogues
import app.contentloader.LoadingType

/**
 * UI model representing state of the main screen.
 *
 * @param activeScreen Current active screen.
 * @param dialogue Current active Dialogue.
 * @param inDarkMode True if we are in dark mode, false if we are not.
 * @param disableMenus True if menus should be disabled, false if not.
 */
data class MainScreenUiModel(
    val activeScreen: ActiveScreen = ActiveScreen.GAME_LIST_SCREEN,
    val dialogue: Dialogues = Dialogues.NONE,
    val inDarkMode: Boolean = false,
    val disableMenus: Boolean = false,
    val loadingType: LoadingType = LoadingType.None
)