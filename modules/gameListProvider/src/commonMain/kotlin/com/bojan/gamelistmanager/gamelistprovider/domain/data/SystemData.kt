package com.bojan.gamelistmanager.gamelistprovider.domain.data

/**
 * Data class containing information about the system itself (Atari 2600, NES, Sega Megadrive... etc).
 *
 * @param name System name.
 * @param software Software used to scrape the item.
 * @param database Database uses to scrape the item.
 * @param systemSubDir Subdirectory of the system (nes, megadrive, n64... etc).
 * @param web Web address of the software used to scrape the item.
 * @param retroArchCoreInfo A [RetroArchCoreInfo] containing recommended core and playlist name.
 */
data class SystemData(
    val name: String,
    val software: String,
    val database: String,
    val systemSubDir: String,
    val web: String,
    val retroArchCoreInfo: RetroArchCoreInfo
)