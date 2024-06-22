package com.bojan.gamelistmanager.gamelistprovider.domain.usecase

import com.bojan.gamelistmanager.gamelistprovider.GAMELIST_FILE_NAME
import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import com.bojan.gamelistmanager.gamelistprovider.domain.interfaces.GameListRepository
import com.bojan.gamelistmanager.gamelistprovider.domain.interfaces.XmlConverter
import kotlinx.coroutines.channels.Channel
import java.io.File

/**
 * UseCase used to scan the provided directory of sub-folders that contain Gamelist.xml file, and populates [GameListRepository] with
 * converted data.
 *
 * @param xmlConverter [XmlConverter] used to convert Gameslit.xml to [GameListData].
 * @param dataSource [GameListRepository] used to store converted information in to the repository.
 */
class LoadGameListUseCase(private val xmlConverter: XmlConverter, private val dataSource: GameListRepository) {

    /**
     * Scans provided directory and populates [GameListRepository] with converted data.
     *
     * @param scanDir Directory to scan.
     * @param update [Channel] used to update the invoker about the currently scanned directory.
     *
     * @return List of failed items.
     */
    suspend operator fun invoke(scanDir: File, update: Channel<Pair<String, Float>>) : List<File> {

        val subdirs = scanDir.listFiles { file -> file.isDirectory }
        val numberOfDirs = subdirs?.size ?: -1
        var scannedDirs = 1

        val gameListContainers = mutableListOf<GameListData>()
        val failedItems = mutableListOf<File>()
        scanDir.walk().filter { it.isDirectory && File(it.parent) == scanDir }.forEach { directory ->
            val gameListFile = File(directory, GAMELIST_FILE_NAME)
            if (gameListFile.exists()) {
                try {
                    val deserialized = xmlConverter.convertXmlToGameListItem(gameListFile)
                    gameListContainers.add(deserialized)
                } catch (t: Throwable) {
                    failedItems.add(gameListFile)
                }
            }
            update.send(Pair(directory.toString(), calculateScanPercentage(numberOfDirs, scannedDirs)))
            scannedDirs++
        }
        dataSource.clearData()
        dataSource.loadGames(gameListContainers.toList())
        return failedItems
    }

    private fun calculateScanPercentage(dirsSize: Int, currentDir: Int) =
        if (dirsSize == -1) {
            -1.0f
        } else {
            currentDir.toFloat() / dirsSize.toFloat()
        }
    }
