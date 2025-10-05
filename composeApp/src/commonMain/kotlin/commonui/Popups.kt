package commonui

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.model.GameListDirectories
import gamelistconverter.composeapp.generated.resources.Res
import gamelistconverter.composeapp.generated.resources.browse_button_symbol
import gamelistconverter.composeapp.generated.resources.cancel
import gamelistconverter.composeapp.generated.resources.ok
import gamelistconverter.composeapp.generated.resources.scanning
import gamelistconverter.composeapp.generated.resources.select_gamelist_dir
import gamelistconverter.composeapp.generated.resources.select_retroarch_dir
import gamelistconverter.composeapp.generated.resources.select_roms_dir
import ktx.autoFocus
import ktx.cancelButton
import ktx.popupOutline
import ktx.thinOutline
import menus.swingchoosers.folderSwingChooser
import org.jetbrains.compose.resources.stringResource

/**
 * Composable used to display popup that shows that something is being loaded.
 *
 * @param text Current status of the loaded items.
 * @param percentage Current percentage of the scan/load.
 * @param onCancel The callback used when user wants to cancel scan/load.
 */
@Composable
fun ScanningPopup(text: String, percentage: Float, onCancel: () -> Unit) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    Dialog(onDismissRequest = {}) {
        Column(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .popupOutline()
                    .background(MaterialTheme.colorScheme.surface)
                    .width(500.dp)
                    .height(150.dp)
                    .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SurfaceText(stringResource(Res.string.scanning))
            Spacer(modifier = Modifier.height(8.dp))
            SurfaceText(text)
            Spacer(modifier = Modifier.height(8.dp))
            if (percentage > 0.0f) {
                LinearProgressIndicator(
                    modifier = Modifier.width(300.dp),
                    progress = { percentage },
                    drawStopIndicator = {},
                    gapSize = 0.dp
                )
            } else {
                LinearProgressIndicator(modifier = Modifier.width(300.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onCancel,
                modifier = Modifier.autoFocus(focusRequester).cancelButton { onCancel() }
            ) {
                Text(stringResource(Res.string.cancel))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

/**
 * Composable used to show simple popup where user can only click OK.
 *
 * @param onOk The callback used when user clicks on OK button.
 * @param content Additional content to show in the popup.
 */
@Composable
fun OkOnlyPopup(onOk: () -> Unit, content: @Composable () -> Unit) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.surface).popupOutline().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            content()
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onOk,
                modifier = Modifier.autoFocus(focusRequester).cancelButton(onOk)
            ) {
                Text(stringResource(Res.string.ok))
            }
        }
    }
}

/**
 * Composable used to show simple popup where user can only click on Cancel.
 *
 * @param onCancel The callback used when user clicks on Cancel button.
 * @param content Additional content to show in the popup.
 */
@Composable
fun PopupWithCancel(onCancel: () -> Unit, content: @Composable () -> Unit) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.surface).popupOutline().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            content()
            Button(
                onClick = onCancel,
                modifier = Modifier.autoFocus(focusRequester).cancelButton(onCancel)
            ) {
                Text(stringResource(Res.string.cancel))
            }
        }
    }
}

/**
 * Composable used to show simple popup where user can only click on Cancel.
 *
 * @param onConfirm The callback used when user clicks on Confirm button
 * @param onCancel The callback used when user clicks on Cancel button.
 * @param content Additional content to show in the popup.
 */
@Composable
fun PopupWithConfirmAndCancel(onCancel: () -> Unit, onConfirm: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.surface).popupOutline().padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            content()
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1.0f))
                Button(
                    onClick = onCancel,
                ) {
                    Text(stringResource(Res.string.cancel))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onConfirm,
                    modifier = Modifier.autoFocus(focusRequester).cancelButton(onCancel)
                ) {
                    Text(stringResource(Res.string.ok))
                }
            }
        }
    }
}

/**
 * Popup with directory pickers for all needed directories for the app.
 *
 * @param onCancel callback invoked when cancel is pressed.
 * @param onConfirm callback invoked when confirm is pressed.
 * @param currentValues a [GameListDirectories] that contain current values for these pickers.
 */
@Composable
fun PopupWithDirectoryPickers(
    onCancel: () -> Unit,
    onConfirm: (GameListDirectories) -> Unit,
    currentValues: GameListDirectories,
) {
    var romsPath by remember { mutableStateOf(currentValues.romsDir) }
    var retroArchPath by remember { mutableStateOf(currentValues.retroArchDir) }
    var gamesListPath by remember { mutableStateOf(currentValues.gameListDir) }

    PopupWithConfirmAndCancel(
        onConfirm = {
            onConfirm(
                GameListDirectories(
                    romsDir = romsPath,
                    retroArchDir = retroArchPath,
                    gameListDir = gamesListPath
                )
            )
        },
        onCancel = onCancel
    ) {
        val romsDirTitle = stringResource(Res.string.select_roms_dir)
        val retroArchDirTitle = stringResource(Res.string.select_retroarch_dir)
        val gameListDirTitle = stringResource(Res.string.select_gamelist_dir)
        ReadOnlyInputTextWithBrowse(
            title = romsDirTitle,
            text = romsPath.path,
            onBrowseClick = {
                folderSwingChooser(
                    title = romsDirTitle,
                    currentDir = romsPath,
                    onFolderSelected = {
                        romsPath = it
                        if (gamesListPath.path == "") {
                            gamesListPath = it
                        }
                    }
                )
            },
            onCancel = onCancel,
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReadOnlyInputTextWithBrowse(
            title = gameListDirTitle,
            text = gamesListPath.path,
            onBrowseClick = {
                folderSwingChooser(
                    title = gameListDirTitle,
                    currentDir = gamesListPath,
                    onFolderSelected = {
                        gamesListPath = it
                        if (romsPath.path == "") {
                            romsPath = it
                        }
                    }
                )
            },
            onCancel = onCancel,
        )
        Spacer(modifier = Modifier.height(16.dp))
        ReadOnlyInputTextWithBrowse(
            title = retroArchDirTitle,
            text = retroArchPath.path,
            onBrowseClick = {
                folderSwingChooser(
                    title = retroArchDirTitle,
                    currentDir = retroArchPath,
                    onFolderSelected = { retroArchPath = it })
            },
            onCancel = onCancel,
        )
    }
}

@Composable
private fun ReadOnlyInputTextWithBrowse(
    title: String,
    text: String,
    onBrowseClick: () -> Unit,
    onCancel: () -> Unit,
) {
    SurfaceText(title)
    Spacer(modifier = Modifier.height(4.dp))
    Row {
        Row(
            modifier = Modifier.thinOutline().height(32.dp).padding(horizontal = 8.dp).weight(1.0f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = text,
                onValueChange = {},
                singleLine = true,
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee()
                    .cancelButton { onCancel() },
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .clickable(onClick = onBrowseClick)
                .size(32.dp)
                .thinOutline()
                .padding(8.dp)
                .cancelButton { onCancel() },
            contentAlignment = Alignment.BottomCenter
        ) {
            SurfaceText(stringResource(Res.string.browse_button_symbol))
        }
    }
}