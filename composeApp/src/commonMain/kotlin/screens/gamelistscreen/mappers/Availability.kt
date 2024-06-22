package screens.gamelistscreen.mappers

import androidx.compose.runtime.Composable
import gamelistconverter.composeapp.generated.resources.Res
import gamelistconverter.composeapp.generated.resources.no
import gamelistconverter.composeapp.generated.resources.yes
import org.jetbrains.compose.resources.stringResource

/**
 * Enum used to display if something is available.
 */
enum class Availability {

    /**
     * Used when item is available.
     */
    YES,

    /**
     * Used when item is not available.
     */
    NO,

    /**
     * Used when availability is unknown.
     */
    UNKNOWN
}

@Composable
fun Availability.toResourceString(): String {
    return when (this) {
        Availability.YES -> stringResource(Res.string.yes)
        Availability.NO -> stringResource(Res.string.no)
        Availability.UNKNOWN -> ""
    }
}