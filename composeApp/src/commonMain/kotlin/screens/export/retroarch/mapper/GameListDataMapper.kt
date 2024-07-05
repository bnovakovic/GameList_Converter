package screens.export.retroarch.mapper

import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameData
import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import com.bojan.gamelistmanager.listexplorer.domain.data.ExportGameData
import com.bojan.gamelistmanager.listexplorer.domain.data.ExportGameListData

fun GameListData.toExportGameListData() = ExportGameListData(
    originalPath = this.originalPath.toString(),
    games = this.games.map { it.toExportGameData() }
)

fun GameData.toExportGameData() = ExportGameData(
    name = this.name,
    hidden = this.hidden,
    path = this.path.toString()
)