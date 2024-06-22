package app.contentloader

/**
 * Represents current loading type.
 */
enum class LoadingType {
    /**
     * ROMs are being loaded.
     */
    ROMS,

    /**
     * RetroArch is being loaded.
     */
    RETRO_ARCH,

    /**
     * Nothing is being loaded at the moment.
     */
    NONE
}