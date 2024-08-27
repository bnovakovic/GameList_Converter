package screens.export.retroarch.mapper

import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameData
import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import com.bojan.gamelistmanager.listexplorer.domain.data.ExportGameData
import com.bojan.gamelistmanager.listexplorer.domain.data.ExportGameListData
import java.io.File

fun GameListData.toExportGameListData(romsDir: File) = ExportGameListData(
    romsPath = File(romsDir, this.system.systemSubDir).toString(),
    games = this.games.map { it.toExportGameData() }
)

fun GameData.toExportGameData() = ExportGameData(
    name = this.name,
    hidden = this.hidden,
    romPath = this.path.toString()
)