package screens.export.retroarch

import PLAYLIST_VERSION
import RETRO_ARCH_CORES_SUBDIR
import RETRO_ARCH_LIST_EXTENSION
import RETRO_ARCH_LIST_SUBDIR
import app.settings.GlmSettings
import app.settings.SettingsKeys
import com.bojan.gamelistconverter.utils.getCoreExtension
import com.bojan.gamelistconverter.utils.getUserHome
import com.bojan.gamelistmanager.commandexecutor.domain.ExecuteCommandUseCase
import com.bojan.gamelistmanager.commandexecutor.domain.config.ExecConfiguration
import com.bojan.gamelistmanager.commandexecutor.repository.ProcessBuilderExecutor
import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import com.bojan.gamelistmanager.gamelistprovider.domain.interfaces.GameListRepository
import com.bojan.gamelistmanager.listexplorer.domain.ConvertGameListUseCase
import com.bojan.gamelistmanager.listexplorer.domain.GameListConvertConfig
import com.bojan.gamelistmanager.listexplorer.domain.PlayListWriteResult
import com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch.RetroArchListConverter
import com.bojan.gamelistmanager.retroarchinfoloader.domain.interfaces.RetroArchInfoDataRepository
import commonui.textlist.SelectableListViewModel
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import screens.export.PlaylistSaveProgress
import screens.export.retroarch.mapper.toExportGameListData
import screens.gamelistscreen.data.CoreInfoUiModel
import screens.gamelistscreen.data.GameSystemUiModel
import screens.gamelistscreen.data.compatibleWithSystem
import screens.gamelistscreen.mappers.toCoreInfoUiModel
import screens.gamelistscreen.mappers.toGameSystemUiModel
import java.io.File
import kotlin.random.Random

/**
 * ViewModel of the export RetroArch list screen.
 *
 * @param coreInfoDataSource DataSource for the core information.
 * @param gameListDataSource DataSource for the game list.
 * @param settings User settings.
 * @param onBack The callback invoked when user wants to go back.
 * @param convertGameListUseCase UseCase used to convert the game list to RetroArch list.
 * @param systemListViewModel ViewModel for the system list.
 * @param coreListViewModel ViewModel for the core list.
 */
class ExportRetroArchScreenViewModel(
    coreInfoDataSource: RetroArchInfoDataRepository,
    gameListDataSource: GameListRepository,
    private val settings: GlmSettings,
    private val onBack: () -> Unit,
    private val convertGameListUseCase: ConvertGameListUseCase = ConvertGameListUseCase(RetroArchListConverter()),
    private val executeCommandUseCase: ExecuteCommandUseCase = ExecuteCommandUseCase(ProcessBuilderExecutor()),
    val systemListViewModel: SelectableListViewModel<GameSystemUiModel> = SelectableListViewModel(),
    val coreListViewModel: SelectableListViewModel<CoreInfoUiModel> = SelectableListViewModel(),
) : ViewModel() {

    private val _uiModel = MutableStateFlow(ExportRetroArchScreenUiModel())
    val uiModel = _uiModel.asStateFlow()
    private var allCoreInfo: List<CoreInfoUiModel> = emptyList()
    private var allGameLists: List<GameListData> = emptyList()
    private var execJob: Job? = null

    init {
        viewModelScope.launch {
            coreInfoDataSource.infoList.collect { infoList ->
                val converted = infoList.map { it.toCoreInfoUiModel() }
                val allCoresSorted = listOf(CoreInfoUiModel.none) + converted.sortedBy { it.text }
                coreListViewModel.setItems(allCoresSorted)
                allCoreInfo = allCoresSorted

                val userPlaylistFolder = settings.getString(SettingsKeys.PLAYLIST_CUSTOM_DIR_KEY)
                if (userPlaylistFolder != null) {
                    _uiModel.value =
                        _uiModel.value.copy(exportPath = File(userPlaylistFolder), exportAllowed = shouldAllowExport(infoList.isNotEmpty()))
                } else {
                    val retroArchDirectory = settings.getString(SettingsKeys.RETRO_ARCH_DIRECTORY_KEY)
                    val retroArchFolder = if (retroArchDirectory != null) File(retroArchDirectory) else getUserHome()
                    _uiModel.value = _uiModel.value.copy(
                        exportPath = File(retroArchFolder, RETRO_ARCH_LIST_SUBDIR),
                        exportAllowed = shouldAllowExport(infoList.isNotEmpty()),
                        isRunAvailable = isRunAvailable(),
                        executeResult = ExportRetroArchScreenUiModel.execResultNone
                    )
                }
                displayCoreListForCurrentSystem()
            }
        }
        viewModelScope.launch {
            gameListDataSource.gameList.collect { systemList ->
                val sorted = systemList.sortedBy { it.system.name }
                val romsDir = settings.getString(SettingsKeys.ROMS_DIRECTORY_KEY)?.let { File(it) }
                if (romsDir != null) {
                    systemListViewModel.setItems(sorted.map { it.toGameSystemUiModel(romsDir) })
                } else {
                    systemListViewModel.setItems(emptyList())
                }
                allGameLists = sorted
                _uiModel.value = _uiModel.value.copy(exportAllowed = shouldAllowExport(systemList.isNotEmpty()))
            }
        }

        viewModelScope.launch {
            systemListViewModel.uiModel.collect {
                selectedSystem(it.selectedItem)
            }
        }

        viewModelScope.launch {
            coreListViewModel.uiModel.collect {
                selectedCore(it.selectedItem)
            }
        }
    }

    private fun selectedSystem(index: Int) {
        _uiModel.value = _uiModel.value.copy(selectedSystem = index)
        displayCoreListForCurrentSystem()
        val coreList = coreListViewModel.uiModel.value.items
        val availableSystems = systemListViewModel.uiModel.value.items
        if (coreList.size <= 1) {
            val selectedSystem = uiModel.value.selectedSystem
            if (availableSystems.size > selectedSystem) {
                val systemInfo = availableSystems[selectedSystem]
                _uiModel.value = _uiModel.value.copy(
                    playlistOptions = listOf(systemInfo.system.retroArchCoreInfo.playlistName),
                    systemInfo = systemInfo,
                    isRunAvailable = isRunAvailable(),
                    executeResult = ExportRetroArchScreenUiModel.execResultNone
                )
            }
        }
        if (availableSystems.size > index && index >= 0) {
            val systemInfo = availableSystems[index]
            _uiModel.value = _uiModel.value.copy(
                numberOfGames = systemInfo.games.size,
                isRunAvailable = isRunAvailable(),
                systemInfo = systemInfo,
                executeResult = ExportRetroArchScreenUiModel.execResultNone
            )
        }
        setCorrectPlaylistOption()
    }

    private fun displayCoreListForCurrentSystem() {
        val selectedSystemIndex: Int = _uiModel.value.selectedSystem
        val allSystems = systemListViewModel.uiModel.value.items.map { it.system }
        if (allSystems.size > selectedSystemIndex) {
            val selectedSystem = allSystems[selectedSystemIndex]
            val coreInfo = selectedSystem.retroArchCoreInfo
            val filename = coreInfo.coreFileName

            val showAllCores = _uiModel.value.showAllCores
            if (showAllCores) {
                val newList = allCoreInfo.map { current ->
                    val compatible = current.compatibleWithSystem(coreInfo)
                    current.copy(compatibleWithActiveSystem = compatible)
                }
                coreListViewModel.setItems(newList)
                val foundRecommendedCore = allCoreInfo.find { it.filename == filename }
                if (foundRecommendedCore != null) {
                    coreListViewModel.selectItem(allCoreInfo.indexOf(foundRecommendedCore))
                }
            } else {
                val filteredCores = allCoreInfo.filter { it.compatibleWithSystem(coreInfo) }
                coreListViewModel.setItems(filteredCores)
                val foundRecommendedCore = filteredCores.find { it.filename == filename }
                if (foundRecommendedCore != null) {
                    coreListViewModel.selectItem(filteredCores.indexOf(foundRecommendedCore))
                }
            }
        }
    }

    private fun isCoreMissing(): Boolean {
        val uiModel = coreListViewModel.uiModel.value

        val retroArchDirectory = settings.getString(SettingsKeys.RETRO_ARCH_DIRECTORY_KEY)
        if (retroArchDirectory != null) {
            val coreList = uiModel.items
            val index = uiModel.selectedItem
            if (coreList.size > index) {
                val coreInfo = coreList[index]
                val coresDir = File(retroArchDirectory, RETRO_ARCH_CORES_SUBDIR)
                val fullCorePath = File(coresDir, "${coreInfo.filename}.${getCoreExtension()}")
                return !fullCorePath.exists() && coreInfo != CoreInfoUiModel.none
            }
        }
        return false
    }

    private fun selectedCore(index: Int) {
        val coreList = coreListViewModel.uiModel.value.items
        val availableSystems = systemListViewModel.uiModel.value.items
        val selectedSystem = uiModel.value.selectedSystem

        if (coreList.size > index && availableSystems.size > selectedSystem) {
            val coreInfo = coreList[index]
            val systemInfo = availableSystems[selectedSystem].system
            val coreMissing = isCoreMissing()
            val playlists = if (coreInfo != CoreInfoUiModel.none) {
                coreInfo.database.split("|")
            } else {
                listOf(systemInfo.retroArchCoreInfo.playlistName)
            }

            val fileName = if (playlists.isNotEmpty()) playlists[0] else ""
            _uiModel.value = _uiModel.value.copy(
                selectedCore = index,
                coreInfo = coreInfo,
                playlistOptions = playlists,
                selectedPlayListOption = 0,
                saveFileName = fileName,
                coreMissing = coreMissing,
                exportAllowed = shouldAllowExport(!coreMissing),
                confirmCoreMissing = !coreMissing,
                isRunAvailable = isRunAvailable(),
                executeResult = ExportRetroArchScreenUiModel.execResultNone
            )
            setCorrectPlaylistOption()
        }
    }

    private fun setCorrectPlaylistOption() {
        val coreList = coreListViewModel.uiModel.value.items
        val availableSystems = systemListViewModel.uiModel.value.items
        val selectedSystem = uiModel.value.selectedSystem
        val selectedCoreIndex = _uiModel.value.selectedCore

        if (coreList.size > selectedCoreIndex && availableSystems.size > selectedSystem) {
            val systemInfo = availableSystems[selectedSystem].system

            val systemCoreInfo = systemInfo.retroArchCoreInfo
            val allPlayListOptions = _uiModel.value.playlistOptions
            val corePlayList = systemCoreInfo.playlistName
            val indexOfPlaylist = allPlayListOptions.indexOf(corePlayList)
            if (indexOfPlaylist >= 0) {
                selectedPlayListOption(indexOfPlaylist)
            }
        }
    }

    /**
     * Option for the playlist name has been selected.
     * @param index index of the selected item.
     */
    fun selectedPlayListOption(index: Int) {
        val uiModel = _uiModel.value
        _uiModel.value = uiModel.copy(
            selectedPlayListOption = index,
            saveFileName = uiModel.playlistOptions[index],
            isRunAvailable = isRunAvailable(),
            executeResult = ExportRetroArchScreenUiModel.execResultNone
        )
    }

    /**
     * Tests selected game by running it in RetroArch.
     */
    fun testSelectedGame() {
        val uiModelValue = _uiModel.value
        val core = uiModelValue.coreInfo
        val retroArchDirectory = settings.getString(SettingsKeys.RETRO_ARCH_DIRECTORY_KEY)

        execJob?.cancel()
        execJob = viewModelScope.launch {
            val romsDir = settings.getString(SettingsKeys.ROMS_DIRECTORY_KEY)
            if (isRunAvailable() && retroArchDirectory != null && romsDir != null && !isCoreMissing()) {
                val systemInfo = uiModelValue.systemInfo
                val games = systemInfo.games
                if (games.isNotEmpty()) {
                    val systemSubDir = systemInfo.gameSystemDirName
                    val romsPath = File(romsDir, systemSubDir)
                    val randomItem = games[Random.nextInt(games.size)]
                    val randomFileName = File(randomItem.romPath).name
                    val fullRomPath = File(romsPath, randomFileName)
                    val config = ExecConfiguration.RunRom(
                        romPath = fullRomPath.toString(),
                        coreFileName = core.filename,
                        retroArchDir = retroArchDirectory
                    )
                    val result = withContext(Dispatchers.IO) {
                        executeCommandUseCase.invoke(config)
                    }
                    _uiModel.value = _uiModel.value.copy(executeResult = result)
                }
            }
        }
    }

    /**
     * Returns back to the previous screen.
     */
    fun goBack() {
        onBack()
    }

    /**
     * Folder for the play list has been selected.
     *
     * @param newFolder newly selected folder.
     */
    fun playlistFolderSelected(newFolder: File) {
        _uiModel.value = _uiModel.value.copy(
            exportPath = newFolder,
            isRunAvailable = isRunAvailable(),
            executeResult = ExportRetroArchScreenUiModel.execResultNone
        )
        settings.putString(SettingsKeys.PLAYLIST_CUSTOM_DIR_KEY, newFolder.toString())
    }


    /**
     * Invoked when user wants to confirm that core is missing.
     *
     * @param confirm True if user has confirmed, false if it is not.
     */
    fun confirmCoreMissing(confirm: Boolean) {
        val uiModel = _uiModel.value
        _uiModel.value = uiModel.copy(
            confirmCoreMissing = confirm,
            exportAllowed = shouldAllowExport(confirm),
            isRunAvailable = isRunAvailable(),
            executeResult = ExportRetroArchScreenUiModel.execResultNone
        )
    }

    private fun shouldAllowExport(attempt: Boolean): Boolean {
        val coreListUiModel = coreListViewModel.uiModel.value
        val gameListUiModel = systemListViewModel.uiModel.value

        val listOfGameList = gameListUiModel.items
        val selectedGame = gameListUiModel.selectedItem

        val listOfCores = coreListUiModel.items
        val selectedCore = coreListUiModel.selectedItem

        return listOfCores.isNotEmpty() && listOfGameList.isNotEmpty() && selectedCore >= 0 && selectedGame >= 0 && attempt
    }

    private fun isRunAvailable(): Boolean {
        val uiModelValue = _uiModel.value
        val core = uiModelValue.coreInfo
        val retroArchDirectory = settings.getString(SettingsKeys.RETRO_ARCH_DIRECTORY_KEY)
        val romsDirectory = settings.getString(SettingsKeys.ROMS_DIRECTORY_KEY)
        val system = uiModelValue.systemInfo
        return core != CoreInfoUiModel.none &&
                retroArchDirectory != null &&
                romsDirectory != null &&
                system != GameSystemUiModel.empty &&
                system.games.isNotEmpty() &&
                !isCoreMissing()
    }

    private fun getActiveSystem(): GameSystemUiModel {
        val systemUiModel = systemListViewModel.uiModel.value
        val systemList = systemUiModel.items
        return systemList[systemUiModel.selectedItem]
    }

    /**
     * Initiate export game list.
     *
     * @param file File where the list should be saved.
     */
    fun exportGameList(file: File) {
        viewModelScope.launch {
            _uiModel.value = _uiModel.value.copy(saveFileResult = PlaylistSaveProgress.SAVING)
            val uiModel = _uiModel.value
            val activeSystem = getActiveSystem()
            val romsDir = settings.getString(SettingsKeys.ROMS_DIRECTORY_KEY)?.let { File(it) }
            val systemDir = romsDir?.let { File(it, activeSystem.gameSystemDirName) }
            val foundSystem = allGameLists.find { it.originalPath == systemDir }

            if (foundSystem != null && romsDir != null) {
                val fullPlaylistName =
                    if (file.extension != RETRO_ARCH_LIST_EXTENSION) "${file.name}.$RETRO_ARCH_LIST_EXTENSION" else file.name
                val selectedCore = coreListViewModel.uiModel.value.items[uiModel.selectedCore]

                val retroArchDirectory = settings.getString(SettingsKeys.RETRO_ARCH_DIRECTORY_KEY)
                val corePath = File(retroArchDirectory, RETRO_ARCH_CORES_SUBDIR)

                var coreName: String? = null
                var coreFullPath: File? = null
                if (selectedCore != CoreInfoUiModel.none) {
                    coreName = selectedCore.displayName
                    coreFullPath = File(corePath, "${selectedCore.filename}.${getCoreExtension()}")
                }

                val config = GameListConvertConfig(
                    outputDir = uiModel.exportPath,
                    outputFileName = fullPlaylistName,
                    gameListData = foundSystem.toExportGameListData(romsDir),
                    coreName = coreName,
                    corePath = coreFullPath,
                    addHidden = false,
                    playlistVersion = PLAYLIST_VERSION,
                )

                when (convertGameListUseCase.invoke(config)) {
                    PlayListWriteResult.SUCCESS -> {
                        _uiModel.value = _uiModel.value.copy(saveFileResult = PlaylistSaveProgress.DONE)
                    }

                    PlayListWriteResult.NO_ACCESS -> {
                        _uiModel.value = _uiModel.value.copy(saveFileResult = PlaylistSaveProgress.NO_ACCESS)
                    }

                    PlayListWriteResult.UNKNOWN_ERROR -> {
                        _uiModel.value = _uiModel.value.copy(saveFileResult = PlaylistSaveProgress.UNKNOWN_ERROR)
                    }
                }
            }
        }
    }

    /**
     * Confirmation for the saving of the playlist.
     */
    fun confirmPlaylistSaveDone() {
        _uiModel.value = _uiModel.value.copy(saveFileResult = PlaylistSaveProgress.NONE)
    }

    /**
     * Show or hide all cores, no matter of the compatibility.
     *
     * @param shouldShow True if all cores should be shown, false it it should not.
     */
    fun changeShowAllCores(shouldShow: Boolean) {
        _uiModel.value = _uiModel.value.copy(
            showAllCores = shouldShow,
            isRunAvailable = isRunAvailable(),
            executeResult = ExportRetroArchScreenUiModel.execResultNone
        )
        displayCoreListForCurrentSystem()
    }
}