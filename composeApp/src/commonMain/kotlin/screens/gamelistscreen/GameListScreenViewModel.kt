package screens.gamelistscreen

import app.settings.GlmSettings
import app.settings.SettingsKeys
import com.bojan.gamelistmanager.gamelistprovider.domain.interfaces.GameListRepository
import commonui.textlist.SelectableListViewModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import screens.gamelistscreen.data.GameInfoUiModel
import screens.gamelistscreen.data.GameListScreenUiModel
import screens.gamelistscreen.data.GameSystemUiModel
import screens.gamelistscreen.mappers.toGameSystemUiModel
import java.io.File

/**
 * Viewmodel used to control GameList screen.
 *
 * @param dataSource Data source of the game list.
 * @param selectableGameListViewModel ViewModel for the selectable game list.
 * @param onExport The callback invoked when export screen should be opened.
 */
class GameListScreenViewModel(
    private val dataSource: GameListRepository,
    private val onExport: () -> Unit,
    private val settings: GlmSettings,
    val selectableGameListViewModel: SelectableListViewModel<GameInfoUiModel> = SelectableListViewModel(),
) : ViewModel() {

    private val _uiModel = MutableStateFlow(GameListScreenUiModel(emptyList(), emptyList()))
    val uiModel = _uiModel.asStateFlow()

    init {
        viewModelScope.launch {
            dataSource.gameList.collect { gameList ->
                val sorted = gameList.sortedBy { it.system.name }
                val romsDir = settings.getString(SettingsKeys.ROMS_DIRECTORY_KEY)?.let { File(it) }
                if (romsDir != null) {
                    val converted = sorted.map { it.toGameSystemUiModel(romsDir) }
                    _uiModel.value = GameListScreenUiModel(
                        gameSystems = converted,
                        gameSystemDisplayList = converted.map { it.text },
                        selectedSystem = if (sorted.isNotEmpty()) 0 else -1,
                        selectedGame = 0,
                        searchQuery = ""
                    )
                } else {
                    _uiModel.value = GameListScreenUiModel(
                        gameSystems = emptyList(),
                        gameSystemDisplayList = emptyList(),
                        selectedSystem = if (sorted.isNotEmpty()) 0 else -1,
                        selectedGame = 0,
                        searchQuery = ""
                    )
                }
                showFullGameList()
                if (sorted.isNotEmpty()) {
                    systemSelected(0)
                    gameSelected(0)
                }
            }
        }

        viewModelScope.launch {
            selectableGameListViewModel.uiModel.collect {
                gameSelected(it.selectedItem)
            }
        }
    }

    fun systemSelected(selectedIndex: Int) {
        val uiModel = _uiModel.value
        val systemList = uiModel.gameSystems
        val selectedGame = if (systemList.isNotEmpty()) 0 else -1
        val activeSystem = if (selectedIndex >= 0 && systemList.size > selectedIndex) {
            systemList[selectedIndex]
        } else {
            GameSystemUiModel.empty
        }
        _uiModel.value = _uiModel.value.copy(
            selectedSystem = selectedIndex,
            selectedGame = selectedGame,
            searchQuery = "",
            activeSystemInfo = activeSystem
        )
        gameSelected(0)
        showFullGameList()
    }

    private fun gameSelected(selectedIndex: Int) {
        val uiModel = _uiModel.value
        val selectedGame = uiModel.selectedGame
        val games = selectableGameListViewModel.uiModel.value.items

        if (games.isNotEmpty() && games.size > selectedGame - 1) {
            _uiModel.value = _uiModel.value.copy(selectedGame = selectedIndex, activeGameInfo = games[selectedIndex])
            return

        }
        _uiModel.value = _uiModel.value.copy(selectedGame = selectedIndex, activeGameInfo = GameListScreenUiModel.emptyGameInfo)
    }

    private fun showFullGameList() {
        val uiModel = _uiModel.value
        val systemList = uiModel.gameSystems
        val selectedSystem = uiModel.selectedSystem
        if (systemList.isNotEmpty() && selectedSystem > -1) {
            val currentGames = systemList[selectedSystem].games
            selectableGameListViewModel.setItems(currentGames)
        } else {
            selectableGameListViewModel.setItems(emptyList())
        }
    }

    fun switchToExportScreen() {
        onExport()
    }
}