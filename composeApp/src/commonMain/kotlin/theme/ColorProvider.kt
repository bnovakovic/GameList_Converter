package theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun extendedColorsProvider(inDarkMode: Boolean): ExtendedColors {
    return if (inDarkMode) {
        ExtendedColors(
            thinOutlineEnabled = Color.LightGray,
            thinOutlineDisabled = GlColors.DarkDisabled,
            contentDisabled = Color.DarkGray,
            accept = GlColors.AcceptDark,
            popupOutline = GlColors.PopupOutlineDark,
            listDisabledText = GlColors.DisabledListItemDark,
            listSelection = Color.DarkGray,
            warning = GlColors.WarningDark
        )
    } else {
        ExtendedColors(
            thinOutlineEnabled = Color.Gray,
            thinOutlineDisabled = Color.LightGray,
            contentDisabled = Color.LightGray,
            accept = GlColors.AcceptLight,
            popupOutline = GlColors.PopupOutlineLight,
            listDisabledText = Color.Gray,
            listSelection = Color.LightGray,
            warning = GlColors.WarningLight
        )
    }
}

fun basicColorProvider(inDarkMode: Boolean) : ColorScheme {
    return if (inDarkMode) {
        darkColorScheme(surface = GlColors.PrimaryDark, secondary = Color(0xFF0066ff))
    } else {
        lightColorScheme(surface = Color.White, secondary = Color.Blue)
    }
}