package app.contentloader

/**
 * Represents current loading type.
 */
sealed class LoadingType {
    /**
     * ROMs are being loaded.
     * @param info Current info on what is loaded.
     * @param progress Current loading progress.
     */
    data class Roms(val info: String, val progress: Float) : LoadingType()

    /**
     * RetroArch is being loaded.
     */
    data object RetroArch : LoadingType()

    /**
     * Nothing is being loaded at the moment.
     */
    data object None : LoadingType()
}