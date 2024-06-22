package screens.gamelistscreen.mappers

import com.bojan.gamelistmanager.retroarchinfoloader.domain.data.RetroArchInfoData
import ktx.toBooleanTextOrUnknown
import screens.gamelistscreen.data.CoreInfoUiModel

fun RetroArchInfoData.toCoreInfoUiModel(): CoreInfoUiModel {
    return CoreInfoUiModel(
        filename = filename,
        displayName = displayName,
        categories = categories,
        authors = authors,
        coreName = coreName,
        supportedExtensions = supportedExtensions,
        license = license,
        permissions = permissions,
        version = version,
        database = database,
        manufacturer = manufacturer,
        systemName = systemName,
        systemId = systemId,
        notes = notes,
        description = description,
        compatibleWithActiveSystem = true,
        cheats = cheats.toBooleanTextOrUnknown(),
        saveStates = saveStates.toBooleanTextOrUnknown()
    )
}