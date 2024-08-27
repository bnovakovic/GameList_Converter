package testutils

import com.bojan.gamelistmanager.listexplorer.domain.data.ExportGameData
import com.bojan.gamelistmanager.listexplorer.domain.data.ExportGameListData

val testGameListData = ExportGameListData(
    games = listOf(
        ExportGameData(
            romPath = ".\\1869 (AGA).zip",
            name = "1869 - Erlebte Geschichte Teil I",
            hidden = false,
        ),
        ExportGameData(
            romPath = ".\\Action Cat (AGA).zip",
            name = "Action Cat",
            hidden = true,
        )
    ),
    romsPath = "src\\commonTest\\testRomsDir\\amiga1200",
)