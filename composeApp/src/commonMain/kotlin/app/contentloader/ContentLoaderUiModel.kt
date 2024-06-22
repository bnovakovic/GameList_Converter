package app.contentloader

/**
 * Data class that represent current loading state.
 * @param loadingType A [LoadingType] that tells us what are we loading at the moment.
 * @param updateInfo Additional information about th current load state (current dir, current file... etc).
 * @param loadingProgress Current progress of the load. Range 0.0f - 1.0f.
 * @param loadingInProgress Is there loading logic happening.
 */
data class ContentLoaderUiModel(
    val loadingType: LoadingType = LoadingType.NONE,
    val updateInfo: String = "",
    val loadingProgress: Float = -1.0f,
)