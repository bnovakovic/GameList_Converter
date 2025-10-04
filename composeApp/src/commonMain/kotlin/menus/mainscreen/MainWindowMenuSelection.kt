package menus.mainscreen

/**
 * Represents all possible menu options.
 */
sealed class MainWindowMenuSelection {

    /**
     * User has selected directory setup menu
     */
    data object DirectorySetup : MainWindowMenuSelection()

    /**
     * User has selected About menu option.
     */
    data object About : MainWindowMenuSelection()

    /**
     * User has selected Scan ROMs menu option.
     */
    data object ScanRoms : MainWindowMenuSelection()

    /**
     * User has selected scan RetroArch dir menu option.
     */
    data object ScanRetroArchDir : MainWindowMenuSelection()

    /**
     * User has selected all menu option.
     */
    data object ScanAll : MainWindowMenuSelection()

    /**
     * User has selected Language menu option.
     *
     * @param locale Selected locale.
     */
    data class LanguageSelected(val locale: String) : MainWindowMenuSelection()

    /**
     * User has selected dark or light mode menu option.
     *
     * @param useDark True if dark, false if light.
     */
    data class UseDarkMode(val useDark: Boolean) : MainWindowMenuSelection()
}