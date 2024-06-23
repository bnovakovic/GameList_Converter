package screens.mainscreen

import GAME_LIST_EDITOR
import GAME_LIST_EDITOR_LINK
import GITHUB_LINK
import KMP_NAME
import KOTLIN_MULTIPLATFORM_LINK
import RECALBOX
import RECALBOX_LINK
import RETROARCH_LINK
import RETROPIE
import RETROPIE_LINK
import RETRO_ARCH
import SCREEN_SCRAPPER
import SCREEN_SCRAPPER_LINK
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import app.ActiveScreen
import app.AppViewModel
import app.Dialogues
import app.contentloader.LoadingType
import commonui.HyperlinkText
import commonui.OkOnlyPopup
import commonui.ScanningPopup
import commonui.SurfaceText
import gamelistconverter.composeapp.generated.resources.Res
import gamelistconverter.composeapp.generated.resources.about_game
import gamelistconverter.composeapp.generated.resources.about_version
import gamelistconverter.composeapp.generated.resources.app_name
import gamelistconverter.composeapp.generated.resources.loading_retroarch_info
import gamelistconverter.composeapp.generated.resources.open_source
import gamelistconverter.composeapp.generated.resources.restart_dialogue_text
import gamelistconverter.composeapp.generated.resources.should_be_made_using_screen_scrapper
import gamelistconverter.composeapp.generated.resources.ui_inspired
import ktx.thinOutline
import org.jetbrains.compose.resources.stringResource
import screens.export.retroarch.ExportRetroArchScreen
import screens.gamelistscreen.GameListScreen
import theme.GlText
import utils.getVersion

/**
 * Composable used to display the main screen.
 *
 * @param appViewModel ViewModel used to control the screen.
 */
@Composable
fun MainScreen(appViewModel: AppViewModel) {
    val uiModel by appViewModel.uiModel.collectAsState()
    when (uiModel.activeScreen) {
        ActiveScreen.GAME_LIST_SCREEN -> {
            GameListScreen(appViewModel.gameListScreenViewModel)
        }

        ActiveScreen.EXPORT_SCREEN -> {
            ExportRetroArchScreen(appViewModel.exportRetroArchScreenViewModel)
        }
    }

    when (uiModel.dialogue) {
        Dialogues.ABOUT -> {
            OkOnlyPopup(onOk = { appViewModel.resetDialogue() }) {
                val appName = stringResource(Res.string.app_name)
                Column(
                    modifier = Modifier.thinOutline().size(450.dp, 400.dp).padding(16.dp).verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    SurfaceText(
                        stringResource(Res.string.about_version, getVersion()),
                        style = GlText.TextOnSurfaceStyle.copy(fontSize = TextUnit(14.0f, TextUnitType.Sp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HyperlinkText(
                        fullText = stringResource(Res.string.about_game, appName, RECALBOX, RETROPIE, RETRO_ARCH),
                        modifier = Modifier.fillMaxWidth(),
                        linkText = listOf(RECALBOX, RETROPIE, RETRO_ARCH),
                        hyperlinks = listOf(RECALBOX_LINK, RETROPIE_LINK, RETROARCH_LINK)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HyperlinkText(
                        fullText = stringResource(Res.string.open_source, appName, KMP_NAME, GITHUB_LINK),
                        linkText = listOf(GITHUB_LINK, KMP_NAME),
                        hyperlinks = listOf(GITHUB_LINK, KOTLIN_MULTIPLATFORM_LINK)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HyperlinkText(
                        fullText = stringResource(Res.string.ui_inspired, GAME_LIST_EDITOR, appName, GAME_LIST_EDITOR),
                        linkText = listOf(GAME_LIST_EDITOR),
                        hyperlinks = listOf(GAME_LIST_EDITOR_LINK)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HyperlinkText(
                        fullText = stringResource(Res.string.should_be_made_using_screen_scrapper, SCREEN_SCRAPPER),
                        linkText = listOf(SCREEN_SCRAPPER),
                        hyperlinks = listOf(SCREEN_SCRAPPER_LINK)
                    )
                }
            }
        }

        Dialogues.RESTART -> {
            OkOnlyPopup(onOk = { appViewModel.resetDialogue() }) {
                SurfaceText(stringResource(Res.string.restart_dialogue_text))
            }
        }

        Dialogues.NONE -> {
            // We show no dialogue
        }
    }

    val contentLoaderUiModel by appViewModel.contentLoader.uiModel.collectAsState()
    when (contentLoaderUiModel.loadingType) {
        LoadingType.ROMS -> {
            ScanningPopup(
                text = contentLoaderUiModel.updateInfo,
                percentage = contentLoaderUiModel.loadingProgress,
                onCancel = { appViewModel.cancelLoad() }
            )
        }

        LoadingType.RETRO_ARCH -> {
            ScanningPopup(
                text = stringResource(Res.string.loading_retroarch_info),
                percentage = contentLoaderUiModel.loadingProgress,
                onCancel = { appViewModel.cancelLoad() })
        }

        LoadingType.NONE -> { // ignore }
        }
    }
}