package app.settings

import com.bojan.gamelistconverter.utils.getWorkingDirectory
import java.io.File
import java.util.Properties

/**
 * Implementation of the [GlmSettings] used to load and save user settings. It uses [Properties] to save user settings.
 */
class GlmPreferences : GlmSettings {
    private val properties: Properties = Properties()

    override fun putString(key: String, value: String) {
        properties[key] = value
        saveToFile()
        cacheSettings()
    }

    override fun putBoolean(key: String, value: Boolean) {
        properties[key] = value.toString()
        saveToFile()
        cacheSettings()
    }

    override fun getString(key: String): String? = properties.getProperty(key)

    override fun getBoolean(key: String): Boolean? = properties.getProperty(key)?.toBooleanStrictOrNull()

    override fun saveToFile() {
        val settingsFile = getSettingsFile()
        settingsFile.parentFile.mkdirs()
        settingsFile.outputStream().use { outputStream ->
            properties.store(outputStream, null)
        }
    }

    override fun cacheSettings() {
        val settingsFile = getSettingsFile()
        if (settingsFile.exists()) {
            settingsFile.inputStream().use { input ->
                properties.load(input)
            }
        }
    }

    private fun getSettingsFile() = File(getWorkingDirectory(), SETTINGS_FILE_NAME)

    companion object {
        const val SETTINGS_FILE_NAME = "GameListConverterSettings.glms"
    }
}