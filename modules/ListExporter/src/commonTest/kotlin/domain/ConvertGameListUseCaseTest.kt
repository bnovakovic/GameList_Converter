package domain

import com.bojan.gamelistmanager.listexplorer.domain.ConvertGameListUseCase
import com.bojan.gamelistmanager.listexplorer.domain.GameListConvertConfig
import com.bojan.gamelistmanager.listexplorer.domain.PlayListWriteResult
import com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch.RetroArchListConverter
import com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch.data.RetroArchGameItem
import com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch.data.RetroArchListObject
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import testutils.fileSeparator
import testutils.testGameListData
import java.io.File

class ConvertGameListUseCaseTest {
    private val tested = ConvertGameListUseCase(RetroArchListConverter())
    private val tempDir = File("build${fileSeparator}temp")

    @Before
    fun setup() {
        tempDir.deleteRecursively()
        tempDir.mkdirs()
    }

    @Test
    fun `test playlist created`(): Unit = runBlocking {
        // GIVEN
        val filename = "MAME.lpl"
        val coreName = "Mame2003plus"
        val corePath = File("core/path/")
        val playlistVersion = "1.0"
        val fullPlaylistPath = File(tempDir, filename)
        val listObject = RetroArchListObject(
            version = playlistVersion,
            defaultCorePath = corePath.toString(),
            defaultCoreName = coreName,
            leftThumbnailMode = 0,
            labelDisplayMode = 0,
            rightThumbnailMode = 0,
            thumbnailMatchMode = 0,
            sortMode = 0,
            scanContentDir = testGameListData.romsPath,
            scanFileExts = "",
            scanDatFilePath = "",
            scanSearchRecursively = true,
            scanSearchArchives = false,
            scanFilterDatContent = false,
            scanOverwritePlaylist = false,
            items = listOf(
                RetroArchGameItem(
                    path = "src\\commonTest\\testRomsDir\\amiga1200\\1869 (AGA).zip",
                    label = "1869 - Erlebte Geschichte Teil I",
                    corePath = "DETECT",
                    coreName = "DETECT",
                    crc32 = "00000000|crc",
                    dbName = "MAME.lpl"
                ),
                RetroArchGameItem(
                    path = "src\\commonTest\\testRomsDir\\amiga1200\\Action Cat (AGA).zip",
                    label = "Action Cat",
                    corePath = "DETECT",
                    coreName = "DETECT",
                    crc32 = "00000000|crc",
                    dbName = "MAME.lpl"
                )
            )
        )

        val config = GameListConvertConfig(
            outputDir = tempDir,
            outputFileName = filename,
            gameListData = testGameListData,
            coreName = coreName,
            corePath = corePath,
            addHidden = true,
            playlistVersion = playlistVersion
        )

        // WHEN
        val result = tested.invoke(config)

        // THEN
        fullPlaylistPath.exists()
        val converted = Json.decodeFromString<RetroArchListObject>(fullPlaylistPath.readText())
        assertEquals(PlayListWriteResult.SUCCESS, result)
        assertEquals(listObject, converted)
    }

    @Test
    fun `access denied`() = runBlocking {
        // GIVEN
        val filename = "MAME.lpl"
        val coreName = "Mame2003plus"
        val corePath = File("core/path/")
        val playlistVersion = "1.0"
        val config = GameListConvertConfig(
            outputDir = tempDir,
            outputFileName = filename,
            gameListData = testGameListData,
            coreName = coreName,
            corePath = corePath,
            addHidden = true,
            playlistVersion = playlistVersion
        )
        tempDir.deleteRecursively()

        // WHEN
        val result = tested.invoke(config)

        // THEN
        assertEquals(PlayListWriteResult.NO_ACCESS, result)
    }

    @After
    fun cleanup() {
        tempDir.deleteRecursively()
    }
}