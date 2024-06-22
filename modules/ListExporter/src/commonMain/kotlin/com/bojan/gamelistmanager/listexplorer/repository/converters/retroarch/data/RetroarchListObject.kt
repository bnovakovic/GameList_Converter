package com.bojan.gamelistmanager.listexplorer.repository.converters.retroarch.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RetroArchListObject(
    val version: String,
    @SerialName("default_core_path")
    val defaultCorePath: String,
    @SerialName("default_core_name")
    val defaultCoreName: String,
    @SerialName("label_display_mode")
    val labelDisplayMode: Int,
    @SerialName("right_thumbnail_mode")
    val rightThumbnailMode: Int,
    @SerialName("left_thumbnail_mode")
    val leftThumbnailMode: Int,
    @SerialName("thumbnail_match_mode")
    val thumbnailMatchMode: Int,
    @SerialName("sort_mode")
    val sortMode: Int,
    @SerialName("scan_content_dir")
    val scanContentDir: String,
    @SerialName("scan_file_exts")
    val scanFileExts: String,
    @SerialName("scan_dat_file_path")
    val scanDatFilePath: String,
    @SerialName("scan_search_recursively")
    val scanSearchRecursively: Boolean,
    @SerialName("scan_search_archives")
    val scanSearchArchives: Boolean,
    @SerialName("scan_filter_dat_content")
    val scanFilterDatContent: Boolean,
    @SerialName("scan_overwrite_playlist")
    val scanOverwritePlaylist: Boolean,
    val items: List<RetroArchGameItem>
)