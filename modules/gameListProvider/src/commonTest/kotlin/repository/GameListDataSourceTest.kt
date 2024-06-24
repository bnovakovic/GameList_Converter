package repository

import com.bojan.gamelistmanager.gamelistprovider.repository.GameListDataSource
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import testutils.testGameListData
import testutils.testGameListDataNoProvider

class GameListDataSourceTest {
    private val tested = GameListDataSource

    @Before
    fun setup() {
        tested.clearData()
    }

    @Test
    fun `test items added`() {
        // THEN
        assert(tested.gameList.value.isEmpty())

        // Given
        val items = listOf(testGameListData, testGameListDataNoProvider)

        // WHEN
        tested.loadGames(items)

        // THEN
        assertEquals(items, tested.gameList.value)
    }

    @Test
    fun `test items clear`() {
        // THEN
        assert(tested.gameList.value.isEmpty())

        // Given
        val items = listOf(testGameListData, testGameListDataNoProvider)

        // WHEN
        tested.loadGames(items)

        // THEN
        assertEquals(items, tested.gameList.value)

        // WHEN
        tested.clearData()

        // THEN
        assert(tested.gameList.value.isEmpty())
    }

    @After
    fun cleanup() {
        tested.clearData()
    }
}