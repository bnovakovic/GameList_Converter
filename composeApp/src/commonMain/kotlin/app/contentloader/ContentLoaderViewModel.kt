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
    private val _uiModel = MutableStateFlow(ContentLoaderUiModel())
    val uiModel = _uiModel.asStateFlow()
    private val channel = Channel<Pair<String, Float>>()
    private var loadJob: Job? = null
    private var cancelingJob = false

    init {
        loadJob = viewModelScope.launch {
            for (update in channel) {
                val progress = update.second
                if (progress < 1.0f && !cancelingJob) {
                    _uiModel.value = _uiModel.value.copy(
                        loadingType = LoadingType.ROMS,
                        updateInfo = update.first,
                        loadingProgress = progress,
                    )
                } else {
                    _uiModel.value = ContentLoaderUiModel()
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
                _uiModel.value = _uiModel.value.copy(
                    loadingType = LoadingType.RETRO_ARCH,
                    loadingProgress = 0.0f,
                )
                loadRetroArchInfoUseCase.invoke(File(retroDir))
                _uiModel.value = ContentLoaderUiModel()
            }
        }
    }

    /**
     * Initiates loading of ROMs information.
     *
     * @param scanDir Root folder of the ROMs directory (example: E:\emulation\roms). Not the specific subdirectory of the system such as
     * E:\emulation\roms\atari2600.
     */
    fun loadRomsInfo(scanDir: String?) {
        scanDir?.let { romsDir ->
            loadJob = viewModelScope.launch {
                cancelingJob = false
                loadGameListUseCase.invoke(File(romsDir), channel)
                _uiModel.value = ContentLoaderUiModel()
            }
        }
    }

    /**
     * Initiates loading of everything all together.
     *
     * @param retroArchDir RetroArch root directory (not any subdirectory inside of RetroArch)
     * @param romsDir Root folder of the ROMs directory (example: E:\emulation\roms). Not the specific subdirectory of the system such as
     * E:\emulation\roms\atari2600.
     */
    fun loadEverything(retroArchDir: String?, romsDir: String?) {
        loadJob = viewModelScope.launch {
            cancelingJob = false
            if (romsDir != null) {
                loadGameListUseCase.invoke(File(romsDir), channel)
            }
            if (retroArchDir != null) {
                _uiModel.value = _uiModel.value.copy(
                    loadingType = LoadingType.RETRO_ARCH,
                    loadingProgress = 0.0f,
                )
                loadRetroArchInfoUseCase.invoke(File(retroArchDir))
            }
            _uiModel.value = ContentLoaderUiModel()
        }
    }

    /**
     * Cancels loading job.
     */
    fun cancelLoadJob() {
        cancelingJob = true
        loadJob?.cancel()
        _uiModel.value = ContentLoaderUiModel()
    }
}