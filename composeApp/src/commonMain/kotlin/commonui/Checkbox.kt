package commonui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import ktx.thinOutline

/**
 * Composable used to display simple checkbox with title.
 *
 * @param checked true if it should be checked, false it it should not.
 * @param onCheckedChange The callback that is triggered when checkbox status is changed.
 * @param title text to show after the checkbox.
 */
@Composable
fun CheckboxWithTitle(checked: Boolean, onCheckedChange: (Boolean) -> Unit, title: String) {
    Row(
        modifier = Modifier
            .height(20.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onCheckedChange(!checked) }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlCheckbox(onCheckedChange, checked)
        Spacer(modifier = Modifier.width(4.dp))
        SurfaceText(title)
    }
}

/**
 * Composable representing custom implementation of the checkbox.
 *
 * @param checked true if it should be checked, false it it should not.
 * @param onCheckedChange The callback that is triggered when checkbox status is changed.
*/
@Composable
fun GlCheckbox(onCheckedChange: (Boolean) -> Unit, checked: Boolean) {
    Box(modifier = Modifier.size(20.dp).thinOutline().clickable { onCheckedChange(!checked) }.fillMaxSize()) {
        AnimatedVisibility(visible = checked, enter = scaleIn(), exit = scaleOut()) {
            Image(Icons.Filled.Check, contentDescription = null, colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface))
        }
    }
}