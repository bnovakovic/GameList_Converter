package menus.mainscreen

import ENGLISH_LANGUAGE_CODE

/**
 * Data class representing UI state of the main screen.
 *
 * @param selectedLanguage Current selected language.
 * @param inDarkMode true if we should be in dark mode, false if we should not.
 */
data class MainScreenMenuUiModel(
    val selectedLanguage: String = ENGLISH_LANGUAGE_CODE,
    val inDarkMode: Boolean = false
)

