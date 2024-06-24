package screens.export.retroarch

import EXPORT_SCREEN_CORE_LIST_OFFSET
import EXPORT_SCREEN_SYSTEM_LIST_OFFSET
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import commonui.CheckboxWithTitle
import commonui.DropDownMenu
import commonui.InfoWithTitle
import commonui.OkOnlyPopup
import commonui.SurfaceText
import commonui.textlist.SearchableTextList
import commonui.textlist.SelectableListViewModel
import gamelistconverter.composeapp.generated.resources.Res
import gamelistconverter.composeapp.generated.resources.authors
import gamelistconverter.composeapp.generated.resources.browse_button_symbol
import gamelistconverter.composeapp.generated.resources.categories
import gamelistconverter.composeapp.generated.resources.cheats
import gamelistconverter.composeapp.generated.resources.core_missing_confirm
import gamelistconverter.composeapp.generated.resources.core_name
import gamelistconverter.composeapp.generated.resources.description
import gamelistconverter.composeapp.generated.resources.export
import gamelistconverter.composeapp.generated.resources.export_path
import gamelistconverter.composeapp.generated.resources.game_list
import gamelistconverter.composeapp.generated.resources.license
import gamelistconverter.composeapp.generated.resources.manufacturer
import gamelistconverter.composeapp.generated.resources.name
import gamelistconverter.composeapp.generated.resources.no_access
import gamelistconverter.composeapp.generated.resources.notes
import gamelistconverter.composeapp.generated.resources.number_of_games
import gamelistconverter.composeapp.generated.resources.overwrite_message
import gamelistconverter.composeapp.generated.resources.overwrite_title
import gamelistconverter.composeapp.generated.resources.playlist_save_done
import gamelistconverter.composeapp.generated.resources.save_retroarch_list_title
import gamelistconverter.composeapp.generated.resources.save_states
import gamelistconverter.composeapp.generated.resources.saving
import gamelistconverter.composeapp.generated.resources.select_core
import gamelistconverter.composeapp.generated.resources.select_playlist
import gamelistconverter.composeapp.generated.resources.select_retroarch_dir
import gamelistconverter.composeapp.generated.resources.select_system
import gamelistconverter.composeapp.generated.resources.show_unsupported_cores
import gamelistconverter.composeapp.generated.resources.supported_extensions
import gamelistconverter.composeapp.generated.resources.system_id
import gamelistconverter.composeapp.generated.resources.system_name
import gamelistconverter.composeapp.generated.resources.unknown_error
import gamelistconverter.composeapp.generated.resources.version
import ktx.thinOutline
import menus.swingchoosers.retroArchSwingChooser
import menus.swingchoosers.saveRetroArchListSwingChooser
import org.jetbrains.compose.resources.stringResource
import screens.export.PlaylistSaveProgress
import screens.gamelistscreen.data.CoreInfoUiModel
import screens.gamelistscreen.data.GameSystemUiModel
import screens.gamelistscreen.mappers.toResourceString
import theme.GameListTheme
import java.io.File

/**
 * Composable used to display RetroArch export screen.
 *
 * @param viewModel ViewModel used to control this screen.
 */
@Composable
fun ExportRetroArchScreen(viewModel: ExportRetroArchScreenViewModel) {
    val uiModel by viewModel.uiModel.collectAsState()
    val title = stringResource(Res.string.select_retroarch_dir)
    Row(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.surface)) {
        SystemAndCoreList(
            modifier = Modifier.weight(1.0f),
            coreViewModel = viewModel.coreListViewModel,
            systemViewModel = viewModel.systemListViewModel,
            showAllCores = uiModel.showAllCores,
            onChangeShowAllCores = viewModel::changeShowAllCores
        )
        InfoAndControls(
            uiModel = uiModel,
            modifier = Modifier.weight(1.0f),
            backClicked = viewModel::goBack,
            export = viewModel::exportGameList,
            browseForRetroArchClick = {
                retroArchSwingChooser(
                    currentDir = uiModel.exportPath,
                    onFolderSelected = viewModel::playlistFolderSelected,
                    title = title
                )
            },
            onSelectedPlaylistOption = viewModel::selectedPlayListOption,
            onConfirmCheckBoxClicked = viewModel::confirmCoreMissing,
            confirmedThatCoreIsMissing = uiModel.confirmCoreMissing,
            numberOfGames = uiModel.numberOfGames
        )
    }
    SavingPlaylistPopup(uiModel.saveFileResult, viewModel::confirmPlaylistSaveDone)
}

@Composable
fun SavingPlaylistPopup(saveProgress: PlaylistSaveProgress, onPopupConfirm: () -> Unit) {
    when (saveProgress) {
        PlaylistSaveProgress.SAVING -> {
            OkOnlyPopup(onOk = onPopupConfirm) {
                Column(modifier = Modifier.width(300.dp).height(100.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    SurfaceText(stringResource(Res.string.saving), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        }

        PlaylistSaveProgress.DONE -> {
            OkOnlyPopup(onOk = onPopupConfirm) {
                Column(modifier = Modifier.width(300.dp).height(100.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Image(imageVector = Icons.Default.CheckCircle, contentDescription = null, colorFilter = ColorFilter.tint(GameListTheme.colors.accept))
                    Spacer(modifier = Modifier.height(8.dp))
                    SurfaceText(stringResource(Res.string.playlist_save_done), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        PlaylistSaveProgress.NO_ACCESS -> {
            OkOnlyPopup(onOk = onPopupConfirm) {
                Column(modifier = Modifier.width(300.dp).height(100.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Image(imageVector = Icons.Default.Warning, contentDescription = null, colorFilter = ColorFilter.tint(MaterialTheme.colors.error))
                    Spacer(modifier = Modifier.height(8.dp))
                    SurfaceText(stringResource(Res.string.no_access), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        PlaylistSaveProgress.UNKNOWN_ERROR -> {
            OkOnlyPopup(onOk = onPopupConfirm) {
                Column(modifier = Modifier.width(300.dp).height(100.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Image(imageVector = Icons.Default.Warning, contentDescription = null, colorFilter = ColorFilter.tint(MaterialTheme.colors.error))
                    Spacer(modifier = Modifier.height(8.dp))
                    SurfaceText(stringResource(Res.string.unknown_error), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        PlaylistSaveProgress.NONE -> {
        }
    }
}

@Composable
fun SystemAndCoreList(
    modifier: Modifier,
    coreViewModel: SelectableListViewModel<CoreInfoUiModel>,
    systemViewModel: SelectableListViewModel<GameSystemUiModel>,
    showAllCores: Boolean,
    onChangeShowAllCores: (Boolean) -> Unit
) {
    Column(modifier = Modifier.then(modifier).fillMaxHeight().padding(8.dp)) {
        SearchableTextList(Modifier.weight(1.0f), systemViewModel, EXPORT_SCREEN_SYSTEM_LIST_OFFSET, stringResource(Res.string.select_system))
        Spacer(modifier = Modifier.height(8.dp))
        SearchableTextList(Modifier.weight(1.0f), coreViewModel, EXPORT_SCREEN_CORE_LIST_OFFSET, stringResource(Res.string.select_core))
        Spacer(modifier = Modifier.height(8.dp))
        CheckboxWithTitle(showAllCores, onChangeShowAllCores, stringResource(Res.string.show_unsupported_cores))
    }
}

@Composable
fun InfoAndControls(
    uiModel: ExportRetroArchScreenUiModel,
    modifier: Modifier,
    backClicked: () -> Unit,
    browseForRetroArchClick: () -> Unit,
    export: (File) -> Unit,
    onSelectedPlaylistOption: (Int) -> Unit,
    confirmedThatCoreIsMissing: Boolean,
    onConfirmCheckBoxClicked: (Boolean) -> Unit,
    numberOfGames: Int
) {
    Column(modifier = Modifier.then(modifier).fillMaxHeight().padding(8.dp)) {
        val coreInfo = uiModel.coreInfo
        val exportPath = uiModel.exportPath
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            InfoWithTitle(stringResource(Res.string.name), coreInfo.displayName, containerModifier = Modifier.weight(1.0f))
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(stringResource(Res.string.number_of_games), numberOfGames.toString(), containerModifier = Modifier.weight(1.0f))
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(stringResource(Res.string.authors), coreInfo.authors, containerModifier = Modifier.weight(1.0f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            InfoWithTitle(stringResource(Res.string.core_name), coreInfo.coreName, containerModifier = Modifier.weight(1.0f))
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(
                stringResource(Res.string.supported_extensions),
                coreInfo.supportedExtensions,
                containerModifier = Modifier.weight(1.0f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(stringResource(Res.string.license), coreInfo.license, containerModifier = Modifier.weight(1.0f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            InfoWithTitle(stringResource(Res.string.system_name), coreInfo.systemName, containerModifier = Modifier.weight(1.0f))
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(stringResource(Res.string.version), coreInfo.version, containerModifier = Modifier.weight(1.0f))
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(stringResource(Res.string.manufacturer), coreInfo.manufacturer, containerModifier = Modifier.weight(1.0f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            InfoWithTitle(
                stringResource(Res.string.cheats),
                coreInfo.cheats.toResourceString(),
                containerModifier = Modifier.weight(1.0f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(
                stringResource(Res.string.save_states),
                coreInfo.saveStates.toResourceString(),
                containerModifier = Modifier.weight(1.0f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            InfoWithTitle(stringResource(Res.string.system_id), coreInfo.systemId, containerModifier = Modifier.weight(1.0f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        InfoWithTitle(
            title = stringResource(Res.string.notes),
            info = coreInfo.notes,
            containerModifier = Modifier.fillMaxWidth().weight(1.0f),
            textModifier = Modifier.fillMaxHeight(),
            useMarquee = false
        )
        Spacer(modifier = Modifier.height(8.dp))
        InfoWithTitle(
            title = stringResource(Res.string.description),
            info = coreInfo.description,
            containerModifier = Modifier.fillMaxWidth().weight(1.0f),
            textModifier = Modifier.fillMaxHeight(),
            useMarquee = false
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            InfoWithTitle(stringResource(Res.string.export_path), exportPath.toString(), containerModifier = Modifier.weight(1.0f))
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier.size(23.dp).thinOutline().clickable { browseForRetroArchClick() },
                contentAlignment = Alignment.BottomCenter
            ) {
                SurfaceText(stringResource(Res.string.browse_button_symbol))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column {
                SurfaceText(text = stringResource(Res.string.select_playlist), modifier = Modifier.padding(start = 0.dp))
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    DropDownMenu(
                        selectedValue = uiModel.selectedPlayListOption,
                        items = uiModel.playlistOptions,
                        onItemSelected = onSelectedPlaylistOption,
                        modifier = Modifier.height(30.dp).weight(1.0f).padding(start = 6.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (uiModel.coreMissing) {
                    CheckboxWithTitle(
                        checked = confirmedThatCoreIsMissing,
                        title = stringResource(Res.string.core_missing_confirm),
                        onCheckedChange = {
                            onConfirmCheckBoxClicked(it)
                        })
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Spacer(modifier = Modifier.weight(1.0f))
            Button(onClick = backClicked, modifier = Modifier.weight(1.0f)) { Text(stringResource(Res.string.game_list)) }
            Spacer(modifier = Modifier.width(8.dp))
            val title = stringResource(Res.string.save_retroarch_list_title)
            val overwriteMessage = stringResource(Res.string.overwrite_message)
            val overwriteTitle = stringResource(Res.string.overwrite_title)
            Button(
                onClick = {
                    saveRetroArchListSwingChooser(
                        currentDir = exportPath,
                        initialFileName = uiModel.saveFileName,
                        onFileConfirm = export,
                        title = title,
                        overwriteMessage = overwriteMessage,
                        overwriteTitle = overwriteTitle
                    )
                },
                modifier = Modifier.weight(1.0f),
                enabled = uiModel.exportAllowed
            ) { Text(stringResource(Res.string.export)) }
        }
    }
}