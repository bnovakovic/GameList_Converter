package theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
object GlText {
    val TextOnSurfaceStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.15.sp
    )

    val InfoTextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 17.sp,
        letterSpacing = 0.15.sp
    )

    val TextOnSurfaceThin = TextStyle(
        fontWeight = FontWeight.Thin,
        fontSize = 15.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.15.sp
    )

    val TitleOnSurfaceStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.15.sp
    )
}