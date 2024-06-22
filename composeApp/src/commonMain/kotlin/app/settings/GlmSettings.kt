package app.settings

/**
 * Settings interface that should be used to manage user settings.
 */
interface GlmSettings {

    /**
     * Gets string for the given key.
     *
     * @param key Settings key for the needed string.
     */
    fun getString(key: String): String?

    /**
     * Gets boolean for the given key.
     *
     * @param key Settings key for the needed Boolean.
     */
    fun getBoolean(key: String): Boolean?

    /**
     * Saves current string in the settings and with the given key.
     *
     * @param key Settings key for the needed string.
     */
    fun putString(key: String, value: String)

    /**
     * Saves current boolean in the settings and with the given key.
     *
     * @param key Settings key for the needed boolean.
     */
    fun putBoolean(key: String, value: Boolean)


    /**
     * Saves current settings to a file.
     */
    fun saveToFile()

    /**
     * Loads user settings from the file and caches it in the settings object.
     */
    fun cacheSettings()

}