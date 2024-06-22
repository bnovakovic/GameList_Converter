package ktx

import screens.gamelistscreen.mappers.Availability

fun String.toBooleanTextOrUnknown() : Availability {
    return if (this.lowercase() == "true") {
        Availability.YES
    } else if (this.lowercase() == "false") {
        Availability.NO
    } else {
        Availability.UNKNOWN
    }
}