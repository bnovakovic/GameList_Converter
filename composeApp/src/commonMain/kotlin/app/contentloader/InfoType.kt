package app.contentloader

/**
 * Enum used to determine which type of information we want to display to the user.
 */
enum class InfoType {
    /**
     * No information to show.
     */
    NONE,

    /**
     * Scan is done, but roms are not found.
     */
    ROMS_NOT_FOUND
}