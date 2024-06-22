package screens.gamelistscreen.data

import com.bojan.gamelistmanager.gamelistprovider.domain.data.RetroArchCoreInfo
import commonui.textlist.SelectableItemUiModel
import screens.gamelistscreen.mappers.Availability

/**
 * Data model for the core information.
 */
data class CoreInfoUiModel(
    val filename: String,
    val displayName: String,
    val categories: String,
    val authors: String,
    val coreName: String,
    val supportedExtensions: String,
    val license: String,
    val permissions: String,
    val version: String,
    val database: String,
    val manufacturer: String,
    val systemName: String,
    val systemId: String,
    val notes: String,
    val description: String,
    val cheats: Availability,
    val saveStates: Availability,
    val compatibleWithActiveSystem: Boolean,
) : SelectableItemUiModel(displayName, !compatibleWithActiveSystem) {
    companion object {
        // This is considered as none CoreInfo. Since all core info is in English, I did the same here, and didn't want to introduce
        // additional complexity to export to string.
        val none = CoreInfoUiModel(
            filename = "",
            displayName = "None",
            categories = "",
            authors = "",
            coreName = "",
            supportedExtensions = "",
            license = "",
            permissions = "",
            version = "",
            database = "",
            manufacturer = "",
            systemName = "",
            systemId = "",
            notes = "",
            description = "No specific core will be selected, so you will have to set it manually in RetroArch",
            cheats = Availability.UNKNOWN,
            saveStates = Availability.UNKNOWN,
            compatibleWithActiveSystem = true
        )
    }
}

/**
 * Extension function used to determine if core is compatible with specific system.
 *
 * @return true if it is, false if it is not.
 */
fun CoreInfoUiModel.compatibleWithSystem(coreInfo: RetroArchCoreInfo): Boolean {
    val playListName = coreInfo.playlistName
    val filename = coreInfo.coreFileName
    return this == CoreInfoUiModel.none || this.filename == filename || (playListName.trim().isNotEmpty() && this.database.contains(
        playListName
    ))
}