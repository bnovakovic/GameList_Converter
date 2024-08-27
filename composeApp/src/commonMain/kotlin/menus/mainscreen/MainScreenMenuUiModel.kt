package menus.mainscreen

import ENGLISH_LANGUAGE_CODE
import java.io.File

/**
 * Data class representing UI state of the main screen.
 *
 * @param selectedGameListDir If not null, it will show games list dir that user has selected.
 * @param selectedRomsDir If not null, it will show ROMs dir that user has selected.
 * @param selectedRetroArchDir If not null, it will show RetroArch dir that user has selected.
 * @param selectedLanguage Current selected language.
 * @param inDarkMode true if we should be in dark mode, false if we should not.
 */
data class MainScreenMenuUiModel(
    val selectedGameListDir: File? = null,
    val selectedRomsDir: File? = null,
    val selectedRetroArchDir: File? = null,
    val selectedLanguage: String = ENGLISH_LANGUAGE_CODE,
    val inDarkMode: Boolean = false
)

