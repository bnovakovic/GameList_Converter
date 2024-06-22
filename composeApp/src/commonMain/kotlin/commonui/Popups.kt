package commonui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import gamelistconverter.composeapp.generated.resources.Res
import gamelistconverter.composeapp.generated.resources.cancel
import gamelistconverter.composeapp.generated.resources.ok
import gamelistconverter.composeapp.generated.resources.scanning
import ktx.autoFocus
import ktx.cancelButton
import ktx.popupOutline
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
                .background(MaterialTheme.colors.surface)
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
                LinearProgressIndicator(progress = percentage, modifier = Modifier.width(300.dp))
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
            modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colors.surface).popupOutline().padding(16.dp),
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