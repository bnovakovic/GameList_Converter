package theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtendedColors(
    val thinOutlineEnabled: Color,
    val thinOutlineDisabled: Color,
    val contentDisabled: Color,
    val accept: Color,
    val popupOutline: Color,
    val listDisabledText: Color,
    val listSelection: Color,
    val warning: Color,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        thinOutlineEnabled = Color.Unspecified,
        thinOutlineDisabled = Color.Unspecified,
        contentDisabled = Color.Unspecified,
        accept = Color.Unspecified,
        popupOutline = Color.Unspecified,
        listDisabledText = Color.Unspecified,
        listSelection = Color.Unspecified,
        warning = Color.Unspecified
    )
}

@Composable
fun GameListTheme(basicColors: ColorScheme = MaterialTheme.colorScheme, extendedColors: ExtendedColors, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = basicColors,
            typography = Typography(),
            content = content
        )
    }
}

object GameListTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}