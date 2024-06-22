package screens.export

/**
 * Enum used to represent current state when list is saved.
 */
enum class PlaylistSaveProgress {
    /**
     * No save in progress.
      */
    NONE,

    /**
     * List is being saved.
     */
    SAVING,

    /**
     * Saving list success.
     */
    DONE,

    /**
     * User does not have access to write to the destination directory.
     */
    NO_ACCESS,

    /**
     * Unknown error happened.
     */
    UNKNOWN_ERROR
}