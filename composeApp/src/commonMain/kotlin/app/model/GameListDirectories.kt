package app.model

import java.io.File

/**
 * Data class used to store all directories application needs to use.
 *
 * @param romsDir directory where your ROM's are located.
 * @param gameListDir directory where gamelist files are located.
 * @param retroArchDir directory of RetroArch
 */
data class GameListDirectories(val romsDir: File, val gameListDir: File, val retroArchDir: File)