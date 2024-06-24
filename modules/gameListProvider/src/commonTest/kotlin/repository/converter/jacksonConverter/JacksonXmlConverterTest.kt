package repository.converter.jacksonConverter

import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jacksonConverter.JacksonXmlConverter
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import testutils.fileSeparator
import testutils.testGameListData
import java.io.File

class JacksonXmlConverterTest {
    private val tested = JacksonXmlConverter()

    @Test
    fun `test jackson conversion`() = runBlocking {
        // GIVEN
        val testFile = File("src${fileSeparator}commonTest${fileSeparator}testRomsDir${fileSeparator}amiga1200", "Gamelist.xml")
        val expected = testGameListData

        // WHEN
        val actual = tested.convertXmlToGameListItem(testFile)

        // THEN
        assertEquals(expected, actual)
    }

}