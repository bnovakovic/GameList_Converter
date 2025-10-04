package menus.mainscreen

import ENGLISH_LANGUAGE_CODE
import app.settings.GlmSettings
import app.settings.SettingsKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel used to handle MainScreen menu.
 *
 * @param settings User settings.
 * @param menuItemSelected Callback used when user has selected any menu option.
 */
class MainScreenMenuViewModel(private val settings: GlmSettings, private val menuItemSelected: (MainWindowMenuSelection) -> Unit) {
    private val _uiModel = MutableStateFlow(MainScreenMenuUiModel())
    val uiModel = _uiModel.asStateFlow()

    /**
     * Updates the UI model with user settings.
     */
    fun loadSettings() {
        _uiModel.value = MainScreenMenuUiModel(
            selectedLanguage = settings.getString(SettingsKeys.SELECTED_LANGUAGE_KEY) ?: ENGLISH_LANGUAGE_CODE,
            inDarkMode = settings.getBoolean(SettingsKeys.DARK_MODE_KEY) ?: false
        )
    }

    /**
     * Notifies that menu has been selected.
     *
     * @param selection Currently selected menu.
     */
    fun menuSelected(selection: MainWindowMenuSelection) {
        menuItemSelected(selection)
        when (selection) {
            is MainWindowMenuSelection.LanguageSelected -> {
                languageSelected(languageCode = selection.locale)
            }

            else -> {
                // not handled here
            }
        }
    }

    /**
     * returns true when RetroArch directory is set, false when it's not.
     */
    fun isRetroArchDirSet() = settings.getString(SettingsKeys.RETRO_ARCH_DIRECTORY_KEY) != null

    /**
     * returns true when game list directory is set, false when it's not.
     */
    fun isGameListDirSet() = settings.getString(SettingsKeys.GAME_LIST_DIRECTORY_KEY) != null

    private fun languageSelected(languageCode: String) {
        _uiModel.value = _uiModel.value.copy(selectedLanguage = languageCode)
    }
}