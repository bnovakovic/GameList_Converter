package app.contentloader

/**
 * Data class that represent current loading state.
 * @param loadingType A [LoadingType] that tells us what are we loading at the moment.
 * @param infoType Type of information to show to the user regarding the current load status.
 */
data class ContentLoaderUiModel(
    val loadingType: LoadingType = LoadingType.None,
    val infoType: InfoType
)