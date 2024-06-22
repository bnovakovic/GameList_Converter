package com.bojan.gamelistmanager.listexplorer.domain

/**
 * UseCase used to create RetroArch playlist.
 *
 * @param gameListConverter converter used to convert the [GameListConvertConfig] in to the RetroArch playlist.
 */
class ConvertGameListUseCase(private val gameListConverter: GameListConverter) {

    /**
     * Invokes the conversion of the list, and saves it to the file.
     *
     * @param exportConfig A [GameListConvertConfig] containing crucial information about the PlayList.
     * @return A [PlayListWriteResult] notifying the actor about the result of the operation.
     */
    suspend operator fun invoke(exportConfig: GameListConvertConfig): PlayListWriteResult {
        val outputDir = exportConfig.outputDir
        return if (outputDir.canWrite()) {
            try {
                gameListConverter.convertList(exportConfig)
                PlayListWriteResult.SUCCESS
            } catch (t: Throwable) {
                PlayListWriteResult.UNKNOWN_ERROR
            }
        } else {
            PlayListWriteResult.NO_ACCESS
        }
    }
}

/**
 * Enum class used to identify the result when list needs to be saved to the desired location.
 */
enum class PlayListWriteResult {
    /**
     * The playlist was successfully saved.
     */
    SUCCESS,
    /**
     * The operation failed due to lack of access permissions on the destination where file had to be saved.
     */
    NO_ACCESS,
    /**
     * An unknown error occurred during the operation.
     */
    UNKNOWN_ERROR
}