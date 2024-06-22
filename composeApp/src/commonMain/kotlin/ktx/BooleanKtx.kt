package ktx

import screens.gamelistscreen.mappers.Availability

fun Boolean.toAvailability() = if (this) Availability.YES else Availability.NO