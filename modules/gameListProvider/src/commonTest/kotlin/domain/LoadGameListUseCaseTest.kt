package domain

import com.bojan.gamelistmanager.gamelistprovider.domain.usecase.LoadGameListUseCase
import com.bojan.gamelistmanager.gamelistprovider.repository.GameListDataSource
import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jsonConverter.JsonXmlConverter
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import testutils.fileSeparator
import testutils.testGameListData
import testutils.testGameListDataNoProvider
import java.io.File

class LoadGameListUseCaseTest {
    private val dataSource = GameListDataSource
    private val tested = LoadGameListUseCase(JsonXmlConverter(), dataSource)

    @Before
    fun setup() {
        dataSource.clearData()
    }

    @Test
    fun `test loadGameListUseCase conversion`(): Unit = runBlocking {
        // GIVEN
        val romsDir = File("src${fileSeparator}commonTest${fileSeparator}testRomsDir")
        val brokenXml = File("src${fileSeparator}commonTest${fileSeparator}testRomsDir${fileSeparator}nes", "gameList.xml")
        val expected = listOf(testGameListData, testGameListDataNoProvider)

        // THEN
        assert(dataSource.gameList.value.isEmpty())

        // WHEN
        val failedItems = tested.invoke(romsDir, null)

        // THEN
        val actual = dataSource.gameList.value
        assertEquals(expected, actual)
        assert(failedItems.contains(brokenXml))
    }
}