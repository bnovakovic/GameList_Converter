package app.contentloader

import com.bojan.gamelistmanager.gamelistprovider.domain.usecase.LoadGameListUseCase
import com.bojan.gamelistmanager.retroarchinfoloader.domain.LoadRetroArchInfoUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

/**
 * ViewModel used to handle loading information needed for the application to function normally. Such as (ROMs or RetroArch Info).
 *
 * @param loadGameListUseCase UseCase used to load game list from the ROMs directory.
 * @param loadRetroArchInfoUseCase UseCase used to load RetroArch information.
 */
class ContentLoaderViewModel(
    private val loadGameListUseCase: LoadGameListUseCase,
    private val loadRetroArchInfoUseCase: LoadRetroArchInfoUseCase,
) : ViewModel() {
    private val _uiModel = MutableStateFlow(ContentLoaderUiModel(infoType = InfoType.NONE))
    val uiModel = _uiModel.asStateFlow()
    private val channel = Channel<Pair<String, Float>>()
    private var loadJob: Job? = null
    private var cancelingJob = false

    init {
        loadJob = viewModelScope.launch {
            for (update in channel) {
                val progress = update.second
                if (progress < 1.0f && !cancelingJob) {
                    _uiModel.value = _uiModel.value.copy(loadingType = LoadingType.Roms(update.first, progress))
                } else {
                    _uiModel.value = _uiModel.value.copy(loadingType = LoadingType.None)
                }
            }
        }
    }

    /**
     * Initiates loading of RetroArch information.
     *
     * @param retroArchDir RetroArch root directory (not any subdirectory inside of RetroArch)
     */
    fun loadRetroArchInformation(retroArchDir: String?) {
        retroArchDir?.let { retroDir ->
            loadJob = viewModelScope.launch {
                cancelingJob = false
                _uiModel.value = _uiModel.value.copy(loadingType = LoadingType.RetroArch)
                loadRetroArchInfoUseCase.invoke(File(retroDir))
                _uiModel.value = _uiModel.value.copy(loadingType = LoadingType.None)
            }
        }
    }

    /**
     * Initiates loading of gamelist information.
     *
     * @param gameListDir Root folder of the XMLs directory (example: E:\emulation\gamelists). Not the specific subdirectory of the system
     * such as E:\emulation\gamelists\atari2600.
     */
    fun loadGameListInfo(gameListDir: File?) {
        if (gameListDir != null) {
            loadJob = viewModelScope.launch {
                _uiModel.value = _uiModel.value.copy(loadingType = LoadingType.None)
                loadGamelistFiles(gameListDir)
            }
        }
    }

    /**
     * Initiates loading of everything all together.
     *
     * @param retroArchDir RetroArch root directory (not any subdirectory inside of RetroArch)
     * @param gameListDir Root folder of the XMLs directory (example: E:\emulation\gamelists). Not the specific subdirectory of the system
     * such as E:\emulation\gamelists\atari2600.
     */
    fun loadEverything(retroArchDir: String?, gameListDir: File?) {
        loadJob = viewModelScope.launch {
            cancelingJob = false
            if (gameListDir != null) {
                loadGamelistFiles(gameListDir)
            }
            if (retroArchDir != null) {
                _uiModel.value = _uiModel.value.copy(
                    loadingType = LoadingType.RetroArch,
                )
                loadRetroArchInfoUseCase.invoke(File(retroArchDir))
            }
            _uiModel.value = _uiModel.value.copy(loadingType = LoadingType.None)
        }
    }

    private suspend fun loadGamelistFiles(gameListDir: File) {
        cancelingJob = false
        val result = loadGameListUseCase.invoke(gameListDir, channel)
        result.onEmptyList {
            _uiModel.value = _uiModel.value.copy(infoType = InfoType.ROMS_NOT_FOUND)
        }.onSuccess {
            println("Game list loaded. Unable to convert ${it.size} files")
        }
    }

    fun resetInfoType() {
        _uiModel.value = _uiModel.value.copy(infoType = InfoType.NONE)
    }

    /**
     * Cancels loading job.
     */
    fun cancelLoadJob() {
        cancelingJob = true
        loadJob?.cancel()
        _uiModel.value = _uiModel.value.copy(loadingType = LoadingType.None)
    }
}