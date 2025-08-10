package commonui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import ktx.thinOutline
import theme.GameListTheme

/**
 * Composable function used to display dropdown menu. Selected value has different color.
 *
 * @param selectedValue Current selected value.
 * @param items items to display.
 * @param onItemSelected The callback that is triggered when new item is selected.
 * @param modifier Modifier used to manage dropdown menu.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenu(
    selectedValue: Int,
    items: List<String>,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val shouldBeEnabled = items.isNotEmpty()
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = if (shouldBeEnabled && items.size > 1) {
                !expanded
            } else {
                false
            }
        },
        modifier = Modifier.thinOutline(shouldBeEnabled)
    ) {
        val onlyOneItem = items.size == 1

        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(8.dp))
                SurfaceText(
                    text = if (items.size > selectedValue && selectedValue >= 0) items[selectedValue] else "",
                )
                Spacer(modifier = Modifier.weight(1.0f))
                Image(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = 8.dp)
                        .rotate(
                            if (expanded)
                                180f
                            else
                                360f
                        ),
                    colorFilter = if (!shouldBeEnabled || onlyOneItem) ColorFilter.tint(GameListTheme.colors.contentDisabled) else ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
        }

        if (!onlyOneItem) {
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                items.forEachIndexed { index, selected: String ->
                    DropdownMenuItem(
                        content = { Text(text = selected) },
                        onClick = {
                            expanded = false
                            onItemSelected(index)
                        },
                        modifier = Modifier.height(24.dp).background(if (index == selectedValue) Color.LightGray else Color.Unspecified),
                    )
                }
            }
        }
    }

}