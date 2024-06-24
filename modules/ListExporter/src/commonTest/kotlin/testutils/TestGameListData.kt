package testutils

import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameData
import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import com.bojan.gamelistmanager.gamelistprovider.domain.data.RetroArchCoreInfo
import com.bojan.gamelistmanager.gamelistprovider.domain.data.SystemData
import java.io.File

val testGameListData = GameListData(
    listOf(
        GameData(
            genreId = 1024,
            image = File("./media/images/1869 (AGA).png"),
            video = null,
            thumbnail = File("./media/box3d/1869 (AGA).png"),
            players = "1-4",
            rating = 0.0f,
            source = "ScreenScraper.fr",
            releaseDate = "19930101T000000",
            path = File("./1869 (AGA).zip"),
            name = "1869 - Erlebte Geschichte Teil I",
            genre = "Simulation",
            publisher = "Max Design",
            developer = "Max Design",
            id = 89378,
            hash = "7F3B2B60",
            desc = "Desc 1.",
            hidden = false,
            adult = true,
            kidGame = false,
            region = "NA",
            favorite = true
        ),
        GameData(
            genreId = 257,
            image = File("./media/images/Action Cat (AGA).png"),
            video = File("./media/videos/Action Cat (AGA).mpg"),
            thumbnail = File("./media/box3d/Action Cat (AGA).png"),
            players = "1",
            rating = 0.7f,
            source = "ScreenScraper.fr",
            releaseDate = "19950101T000000",
            path = File("./Action Cat (AGA).zip"),
            name = "Action Cat",
            genre = "Action-Platform",
            publisher = "1001 Software",
            developer = "1001 Software",
            id = 172940,
            hash = "6A51F8D0",
            desc = "Desc 2.",
            hidden = true,
            adult = false,
            kidGame = true,
            region = "EU, NA",
            favorite = false
        )
    ),
    originalPath = File("src\\commonTest\\testRomsDir\\amiga1200"),
    system = SystemData(
        name = "Commodore Amiga 1200",
        software = "Skraper",
        database = "ScreenScraper.fr",
        web = "http://www.screenscraper.fr",
        RetroArchCoreInfo("puae_libretro", "Commodore - Amiga")
    )
)

val testGameListDataNoProvider = GameListData(
    listOf(
        GameData(
            genreId = 1024,
            image = File("./media/images/1869 (AGA).png"),
            video = null,
            thumbnail = File("./media/box3d/1869 (AGA).png"),
            players = "1-4",
            rating = 0.0f,
            source = "ScreenScraper.fr",
            releaseDate = "19930101T000000",
            path = File("./1869 (AGA).zip"),
            name = "1869 - Erlebte Geschichte Teil I",
            genre = "Simulation",
            publisher = "Max Design",
            developer = "Max Design",
            id = 89378,
            hash = "7F3B2B60",
            desc = "Desc 1.",
            hidden = false,
            adult = true,
            kidGame = false,
            region = "NA",
            favorite = true
        ),
        GameData(
            genreId = 257,
            image = File("./media/images/Action Cat (AGA).png"),
            video = File("./media/videos/Action Cat (AGA).mpg"),
            thumbnail = File("./media/box3d/Action Cat (AGA).png"),
            players = "1",
            rating = 0.7f,
            source = "ScreenScraper.fr",
            releaseDate = "19950101T000000",
            path = File("./Action Cat (AGA).zip"),
            name = "Action Cat",
            genre = "Action-Platform",
            publisher = "1001 Software",
            developer = "1001 Software",
            id = 172940,
            hash = "6A51F8D0",
            desc = "Desc 2.",
            hidden = true,
            adult = false,
            kidGame = true,
            region = "EU, NA",
            favorite = false
        )
    ),
    originalPath = File("src\\commonTest\\testRomsDir\\unknownFolder"),
    system = SystemData(
        name = "Unknown",
        software = "",
        database = "",
        web = "",
        RetroArchCoreInfo.empty
    )
)