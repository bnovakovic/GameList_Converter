package repository.converter.jsonConverter

import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jsonConverter.JsonXmlConverter
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import testutils.fileSeparator
import testutils.testGameListData
import testutils.testGameListDataNoProvider
import java.io.File

class JasonXmlConverterTest {
    private val tested = JsonXmlConverter()

    @Test
    fun `test jason conversion`() = runBlocking {
        // GIVEN
        val testFile = File("src${fileSeparator}commonTest${fileSeparator}testRomsDir${fileSeparator}amiga1200", "Gamelist.xml")
        val expected = testGameListData

        // WHEN
        val actual = tested.convertXmlToGameListItem(testFile)

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun `test jason conversion without provider and in unknown directory`() = runBlocking {
        // GIVEN
        val testFile = File("src${fileSeparator}commonTest${fileSeparator}testRomsDir${fileSeparator}unknownFolder", "Gamelist.xml")
        val expected = testGameListDataNoProvider

        // WHEN
        val actual = tested.convertXmlToGameListItem(testFile)

        // THEN
        assertEquals(expected, actual)
    }
}