package app

import app.contentloader.ContentLoaderViewModel
import app.contentloader.LoadingType
import app.settings.GlmPreferences
import app.settings.GlmSettings
import app.settings.SettingsKeys
import com.bojan.gamelistmanager.gamelistprovider.domain.usecase.LoadGameListUseCase
import com.bojan.gamelistmanager.gamelistprovider.repository.GameListDataSource
import com.bojan.gamelistmanager.gamelistprovider.repository.converter.jacksonConverter.JacksonXmlConverter
import com.bojan.gamelistmanager.retroarchinfoloader.domain.LoadRetroArchInfoUseCase
import com.bojan.gamelistmanager.retroarchinfoloader.repository.RetroArchInfoDataSource
import com.bojan.gamelistmanager.retroarchinfoloader.repository.RetroArchInfoReaderAsProperties
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import menus.mainscreen.MainScreenMenuViewModel
import menus.mainscreen.MainScreenUiModel
import menus.mainscreen.MainWindowMenuSelection
import screens.export.retroarch.ExportRetroArchScreenViewModel
import screens.gamelistscreen.GameListScreenViewModel
import java.util.Locale

class AppViewModel(private val onRequestApplicationClose: () -> Unit) : ViewModel() {
    private val gameListDataSource = GameListDataSource
    private val coreInfoDataSource = RetroArchInfoDataSource
    private val loadGameListUseCase: LoadGameListUseCase = LoadGameListUseCase(JacksonXmlConverter(), gameListDataSource)
    private val loadRetroArchInfoUseCase = LoadRetroArchInfoUseCase(
        reader = RetroArchInfoReaderAsProperties(),
        dataSource = coreInfoDataSource
    )
    private val appSettings: GlmSettings = GlmPreferences()

    val mainMenuViewModel = MainScreenMenuViewModel(settings = appSettings, menuItemSelected = ::mainScreenMenuItemSelected)
    val contentLoader = ContentLoaderViewModel(loadGameListUseCase, loadRetroArchInfoUseCase)
    val gameListScreenViewModel = GameListScreenViewModel(
        dataSource = gameListDataSource,
        onExport = { _uiModel.value = _uiModel.value.copy(activeScreen = ActiveScreen.EXPORT_SCREEN) }
    )

    val exportRetroArchScreenViewModel = ExportRetroArchScreenViewModel(
        settings = appSettings,
        coreInfoDataSource = coreInfoDataSource,
        gameListDataSource = gameListDataSource,
        onBack = { _uiModel.value = _uiModel.value.copy(activeScreen = ActiveScreen.GAME_LIST_SCREEN) }
    )
    private val _uiModel = MutableStateFlow(MainScreenUiModel())
    val uiModel = _uiModel.asStateFlow()

    init {
        appSettings.cacheSettings()
        mainMenuViewModel.settingsUpdated()
        val retroArchDir = appSettings.getString(SettingsKeys.RETRO_ARCH_DIRECTORY_KEY)
        val romsDir = appSettings.getString(SettingsKeys.ROMS_DIRECTORY_KEY)
        val selectedLanguage = appSettings.getString(SettingsKeys.SELECTED_LANGUAGE_KEY)
        val inDarkMode = appSettings.getBoolean(SettingsKeys.DARK_MODE_KEY)
        inDarkMode?.let {
            _uiModel.value = _uiModel.value.copy(inDarkMode = inDarkMode)
        }
        selectedLanguage?.let {
            Locale.setDefault(Locale(it))
        }

        viewModelScope.launch {
            contentLoader.uiModel.collect {
                _uiModel.value = _uiModel.value.copy(workInProgress = it.loadingType != LoadingType.NONE)
            }
        }
        contentLoader.loadEverything(retroArchDir, romsDir)
    }

    private fun mainScreenMenuItemSelected(selection: MainWindowMenuSelection) {
        val retroArchDir = appSettings.getString(SettingsKeys.RETRO_ARCH_DIRECTORY_KEY)
        val romsDir = appSettings.getString(SettingsKeys.ROMS_DIRECTORY_KEY)

        when (selection) {
            is MainWindowMenuSelection.SelectedRoms -> {
                contentLoader.loadRomsInfo(selection.directory.toString())
            }

            is MainWindowMenuSelection.SelectedRetroArchDirectory -> {
                contentLoader.loadRetroArchInformation(selection.directory.toString())
            }

            is MainWindowMenuSelection.About -> {
                _uiModel.value = _uiModel.value.copy(dialogue = Dialogues.ABOUT)
            }

            is MainWindowMenuSelection.ScanRoms -> {
                contentLoader.loadRomsInfo(romsDir)
            }

            is MainWindowMenuSelection.ScanRetroArchDir -> {
                contentLoader.loadRetroArchInformation(retroArchDir)
            }

            is MainWindowMenuSelection.ScanAll -> {
                contentLoader.loadEverything(retroArchDir, romsDir)
            }

            is MainWindowMenuSelection.LanguageSelected -> {
                appSettings.putString(SettingsKeys.SELECTED_LANGUAGE_KEY, selection.locale)
                _uiModel.value = _uiModel.value.copy(dialogue = Dialogues.RESTART)
            }

            is MainWindowMenuSelection.UseDarkMode -> {
                appSettings.putBoolean(SettingsKeys.DARK_MODE_KEY, selection.useDark)
                mainMenuViewModel.settingsUpdated()
                _uiModel.value = _uiModel.value.copy(inDarkMode = selection.useDark)
            }
        }
    }

    fun goBack() {
        val uiModel = _uiModel.value
        if (uiModel.dialogue != Dialogues.NONE) {
            resetDialogue()
            return
        }

        when (uiModel.activeScreen) {
            ActiveScreen.GAME_LIST_SCREEN -> {
                onRequestApplicationClose()
            }

            ActiveScreen.EXPORT_SCREEN -> {
                _uiModel.value = _uiModel.value.copy(activeScreen = ActiveScreen.GAME_LIST_SCREEN)
            }
        }
    }

    fun cancelLoad() {
        contentLoader.cancelLoadJob()
    }

    fun resetDialogue() {
        _uiModel.value = _uiModel.value.copy(dialogue = Dialogues.NONE)
    }
}

enum class ActiveScreen { GAME_LIST_SCREEN, EXPORT_SCREEN }
enum class Dialogues { ABOUT, RESTART, NONE }