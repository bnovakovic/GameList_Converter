package domain

import com.bojan.gamelistmanager.retroarchinfoloader.domain.LoadRetroArchInfoUseCase
import com.bojan.gamelistmanager.retroarchinfoloader.domain.data.RetroArchInfoData
import com.bojan.gamelistmanager.retroarchinfoloader.repository.RetroArchInfoDataSource
import com.bojan.gamelistmanager.retroarchinfoloader.repository.RetroArchInfoReaderAsProperties
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

class LoadRetroArchInfoUseCaseTest {
    private val dataSource = RetroArchInfoDataSource
    private val tested = LoadRetroArchInfoUseCase(RetroArchInfoReaderAsProperties(), dataSource)

    private val fileSeparator get() = System.getProperty("file.separator")
    private val infoDir = File("src${fileSeparator}commonTest${fileSeparator}retroArchDir")

    @Before
    fun setup() {
        dataSource.clearData()
    }

    @Test
    fun `test load RetroArch info data`() = runBlocking {
        // GIVEN
        val expectedList = listOf(
            RetroArchInfoData(
                filename = "test_core1",
                displayName = "Test1",
                categories = "Test category",
                authors = "Team Fragile",
                coreName = "Test core 1",
                license = "Apache",
                permissions = "permission 1",
                version = "v1.0",
                database = "MAME|FBA",
                manufacturer = "Fragile software",
                systemName = "Fragile system",
                systemId = "fs0.5b",
                notes = "test core note 1",
                description = "test core number 1",
                cheats = "true",
                saveStates = "true",
                supportedExtensions = "rom|fbrom"
            ),
            RetroArchInfoData(
                filename = "test_core2",
                displayName = "Test2",
                categories = "Test 2 category",
                authors = "Team Fragile North",
                coreName = "Test core 2",
                license = "MIT",
                permissions = "permission 2",
                version = "v2.0",
                database = "GBA|NES|SNES",
                manufacturer = "Fragile software 2",
                systemName = "Fragile system 2",
                systemId = "fs1.5b",
                notes = "test core note 2",
                description = "test core number 2",
                cheats = "false",
                saveStates = "false",
                supportedExtensions = "nes|fds",
            )
        )

        // WHEN
        tested.invoke(infoDir)

        // THEN
        assertEquals(expectedList, dataSource.infoList.value)

        // WHEN
        dataSource.clearData()

        // THEN
        assert(dataSource.infoList.value.isEmpty())
    }

    @After
    fun cleanup() {
        dataSource.clearData()
    }
}