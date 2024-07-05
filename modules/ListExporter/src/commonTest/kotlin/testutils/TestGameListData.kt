package testutils

import com.bojan.gamelistmanager.listexplorer.domain.data.ExportGameData
import com.bojan.gamelistmanager.listexplorer.domain.data.ExportGameListData

val testGameListData = ExportGameListData(
    games = listOf(
        ExportGameData(
            path = ".\\1869 (AGA).zip",
            name = "1869 - Erlebte Geschichte Teil I",
            hidden = false,
        ),
        ExportGameData(
            path = ".\\Action Cat (AGA).zip",
            name = "Action Cat",
            hidden = true,
        )
    ),
    originalPath = "src\\commonTest\\testRomsDir\\amiga1200",
)