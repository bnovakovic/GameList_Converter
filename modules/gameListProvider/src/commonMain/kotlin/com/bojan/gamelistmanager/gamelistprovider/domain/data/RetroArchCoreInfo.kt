package com.bojan.gamelistmanager.gamelistprovider.domain.data

/**
 * Data class containing RetroArch core information.
 *
 * @param coreFileName Filename of the core.
 * @param playlistName Playlist name for this core.
 */
data class RetroArchCoreInfo(val coreFileName: String, val playlistName: String) {
    companion object {
        val empty = RetroArchCoreInfo("", "")
    }
}