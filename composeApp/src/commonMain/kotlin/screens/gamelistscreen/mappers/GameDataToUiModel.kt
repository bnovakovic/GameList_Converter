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
import java.time.format.DateTimeParseException

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
    if (dateString.isBlank() || dateString.equals("null", ignoreCase = true)) {
        return ""
    }

    val outputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    // A list of formatters to try for date-time values.
    val dateTimePatterns = listOf(
        "yyyyMMdd'T'HHmmss",
        "yyyy-MM-dd'T'HH:mm:ss",
        "yyyy-MM-dd HH:mm:ss",
        "yyyyMMddHHmmss"
    )

    for (pattern in dateTimePatterns) {
        try {
            val formatter = DateTimeFormatter.ofPattern(pattern)
            val dateTime = LocalDateTime.parse(dateString, formatter)
            return dateTime.format(outputFormat)
        } catch (e: DateTimeParseException) {
            // Ignore and try the next format
        }
    }

    // A list of formatters to try for date-only values.
    val datePatterns = listOf(
        "yyyyMMdd",
        "yyyy-MM-dd"
    )

    for (pattern in datePatterns) {
        try {
            val formatter = DateTimeFormatter.ofPattern(pattern)
            val date = LocalDate.parse(dateString, formatter)
            return date.format(outputFormat)
        } catch (e: DateTimeParseException) {
            // Ignore and try the next format
        }
    }

    // If all formats fail, check if it's just a year.
    if (dateString.length == 4 && dateString.all { it.isDigit() }) {
        return dateString
    }

    // For other invalid formats, return an empty string to avoid displaying garbage.
    return ""
}