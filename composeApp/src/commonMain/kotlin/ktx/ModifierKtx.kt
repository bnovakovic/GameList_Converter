package ktx

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import theme.GameListTheme

@Composable
fun Modifier.thinOutline(enabled: Boolean = true) = this.border(
    width = 1.dp,
    color = if (enabled) GameListTheme.colors.thinOutlineEnabled else GameListTheme.colors.thinOutlineDisabled,
    shape = RoundedCornerShape(2.dp)
)

@Composable
fun Modifier.popupOutline() = this.border(2.dp, GameListTheme.colors.popupOutline, RoundedCornerShape(10.dp))

@Composable
fun Modifier.cancelButton(onCancel: () -> Unit) = this.onKeyEvent {
    if (it.type == KeyEventType.KeyDown && it.key == Key.Escape) {
        onCancel()
        return@onKeyEvent true
    }
    return@onKeyEvent false
}

@Composable
fun Modifier.autoFocus(focusRequester: FocusRequester) = this.focusRequester(focusRequester).onGloballyPositioned { focusRequester.requestFocus() }