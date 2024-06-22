package screens.gamelistscreen.data

import screens.gamelistscreen.mappers.Availability

/**
 * UI model for the game list screen.
 */
data class GameListScreenUiModel(
    val gameSystems: List<GameSystemUiModel>,
    val gameSystemDisplayList: List<String>,
    val selectedSystem: Int = 0,
    val selectedGame: Int = -1,
    val activeGameInfo: GameInfoUiModel = emptyGameInfo,
    val searchQuery: String = "",
) {
    companion object {
        val emptyGameInfo = GameInfoUiModel(
            gameName = "",
            imagePath = null,
            videoPath = null,
            region = "",
            date = "",
            players = "",
            rating = "",
            kidGame = Availability.UNKNOWN,
            hidden = Availability.UNKNOWN,
            favorite = Availability.UNKNOWN,
            publisher = "",
            developer = "",
            genre = "",
            description = "",
            romPath = ""
        )
    }
}