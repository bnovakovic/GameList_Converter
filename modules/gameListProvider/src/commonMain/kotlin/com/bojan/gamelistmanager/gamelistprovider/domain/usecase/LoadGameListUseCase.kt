package com.bojan.gamelistmanager.gamelistprovider.domain.usecase

import LoadReadResult
import com.bojan.gamelistmanager.gamelistprovider.GAMELIST_FILE_NAME
import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import com.bojan.gamelistmanager.gamelistprovider.domain.interfaces.GameListRepository
import com.bojan.gamelistmanager.gamelistprovider.domain.interfaces.XmlConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext
import java.io.File

/**
 * UseCase used to scan the provided directory of sub-folders that contain Gamelist.xml file, and populates [GameListRepository] with
 * converted data.
 *
 * @param xmlConverter [XmlConverter] used to convert Gamelist.xml to [GameListData].
 * @param dataSource [GameListRepository] used to store converted information in to the repository.
 */
class LoadGameListUseCase(private val xmlConverter: XmlConverter, private val dataSource: GameListRepository) {

    /**
     * Scans provided directory and populates [GameListRepository] with converted data.
     *
     * @param scanDir Directory to scan.
     * @param update [Channel] used to update the invoker about the currently scanned directory.
     *
     * @return custom result with List of failed items. Returns [LoadReadResult.EmptyList] in case no games were found.
     */
    suspend operator fun invoke(scanDir: File, update: Channel<Pair<String, Float>>?): LoadReadResult {

        val subdirs = scanDir.listFiles { file -> file.isDirectory }
        val numberOfDirs = subdirs?.size ?: -1
        var scannedDirs = 0

        val gameListContainers = mutableListOf<GameListData>()
        val failedItems = mutableListOf<File>()

        withContext(Dispatchers.IO) {
            subdirs?.forEach { romsDir ->
                if (romsDir.isDirectory && File(romsDir.parent) == scanDir) {
                    val gameListFile = File(romsDir, GAMELIST_FILE_NAME)
                    if (gameListFile.exists()) {
                        try {
                            val deserialized = xmlConverter.convertXmlToGameListItem(gameListFile)
                            gameListContainers.add(deserialized)
                        } catch (t: Throwable) {
                            failedItems.add(gameListFile)
                            t.printStackTrace()
                        }
                    }
                    scannedDirs++
                    update?.send(Pair(romsDir.toString(), calculateScanPercentage(numberOfDirs, scannedDirs)))
                }
            }
        }

        dataSource.clearData()
        dataSource.loadGames(gameListContainers.toList())
        return if (gameListContainers.isEmpty()) {
            LoadReadResult.EmptyList
        } else {
            LoadReadResult.Success(failedItems)
        }
    }

    private fun calculateScanPercentage(dirsSize: Int, currentDir: Int) =
        if (dirsSize == -1) {
            -1.0f
        } else {
            currentDir.toFloat() / dirsSize.toFloat()
        }
}
