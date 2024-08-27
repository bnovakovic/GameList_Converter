package menus.mainscreen

import ENGLISH_LANGUAGE_CODE
import app.settings.GlmSettings
import app.settings.SettingsKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

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
    fun settingsUpdated() {
        _uiModel.value = MainScreenMenuUiModel(
            selectedGameListDir = settings.getString(SettingsKeys.GAME_LIST_DIRECTORY_KEY)?.let { File(it) },
            selectedRomsDir = settings.getString(SettingsKeys.ROMS_DIRECTORY_KEY)?.let { File(it) },
            selectedRetroArchDir = settings.getString(SettingsKeys.RETRO_ARCH_DIRECTORY_KEY)?.let { File(it) },
            selectedLanguage = settings.getString(SettingsKeys.SELECTED_LANGUAGE_KEY) ?: ENGLISH_LANGUAGE_CODE,
            inDarkMode = settings.getBoolean(SettingsKeys.DARK_MODE_KEY) ?: false
        )
    }
    private fun romsDirSelected(dir: File) {
        _uiModel.value = _uiModel.value.copy(selectedRomsDir = dir)
        settings.putString(SettingsKeys.ROMS_DIRECTORY_KEY, dir.toString())
    }

    private fun gameListDirSelected(dir: File) {
        _uiModel.value = _uiModel.value.copy(selectedGameListDir = dir)
        settings.putString(SettingsKeys.GAME_LIST_DIRECTORY_KEY, dir.toString())
    }

    private fun retroArchDirSelected(dir: File) {
        _uiModel.value = _uiModel.value.copy(selectedRetroArchDir = dir)
        settings.putString(SettingsKeys.RETRO_ARCH_DIRECTORY_KEY, dir.toString())
    }

    private fun languageSelected(languageCode: String) {
        _uiModel.value = _uiModel.value.copy(selectedLanguage = languageCode)
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

            is MainWindowMenuSelection.SelectedRoms -> {
                romsDirSelected(selection.directory)
            }

            is MainWindowMenuSelection.SelectedRetroArchDirectory -> {
                retroArchDirSelected(selection.directory)
            }

            is MainWindowMenuSelection.SelectedGamesListsDir -> {
                gameListDirSelected(selection.directory)
            }

            else -> {
                // not handled here
            }
        }
    }
}