package screens.gamelistscreen.mappers

import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameData
import com.bojan.gamelistmanager.gamelistprovider.domain.data.GameListData
import com.bojan.gamelistmanager.gamelistprovider.domain.data.SystemData
import ktx.toAvailability
import screens.gamelistscreen.data.GameInfoUiModel
import screens.gamelistscreen.data.GameSystemUiModel
import screens.gamelistscreen.data.SystemUiModel
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun GameListData.toGameSystemUiModel(romsPath: File) = GameSystemUiModel(
    system = this.system.toSystemUiModel(),
    games = this.games.map { it.toGameInfoUiModel(File(romsPath, this.system.systemSubDir)) },
    gameSystemDirName = this.system.systemSubDir
)

fun GameData.toGameInfoUiModel(basicPath: File): GameInfoUiModel {
    return GameInfoUiModel(
        gameName = this.name,
        imagePath = if (this.image != null) File(basicPath, this.image.toString().substring(1)) else null,
        videoPath = if (this.video != null) File(basicPath, this.video.toString().substring(1)) else null,
        region = this.region ?: "",
        date = convertToReadableDate(this.releaseDate.toString()),
        players = this.players ?: "",
        rating = this.rating.toString(),
        kidGame = this.kidGame.toAvailability(),
        hidden = this.hidden.toAvailability(),
        favorite = this.favorite.toAvailability(),
        publisher = this.publisher ?: "",
        developer =this.developer ?: "",
        genre = this.genre ?: "",
        description = this.desc ?: "",
        romPath = this.path.toString()
    )
}

fun SystemData.toSystemUiModel() : SystemUiModel = SystemUiModel(this.name, this.systemSubDir, this.retroArchCoreInfo)

fun convertToReadableDate(dateString: String): String {
    if (dateString.isBlank() || dateString.equals("null", ignoreCase = true)) return ""

    val outputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    val dateTimeFormats = listOf(
        "yyyyMMdd'T'HHmmss",
        "yyyy-MM-dd'T'HH:mm:ss",
        "yyyy-MM-dd HH:mm:ss",
        "yyyyMMddHHmmss"
    )

    val dateFormats = listOf(
        "yyyyMMdd",
        "yyyy-MM-dd"
    )

    fun tryParseDateTime(): String? = dateTimeFormats.firstNotNullOfOrNull { pattern ->
        runCatching { LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(pattern)).format(outputFormat) }.getOrNull()
    }

    fun tryParseDate(): String? = dateFormats.firstNotNullOfOrNull { pattern ->
        runCatching { LocalDate.parse(dateString, DateTimeFormatter.ofPattern(pattern)).format(outputFormat) }.getOrNull()
    }

    return tryParseDateTime()
        ?: tryParseDate()
        ?: dateString.takeIf { it.length == 4 && it.all(Char::isDigit) }
        ?: ""
}