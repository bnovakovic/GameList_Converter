package com.bojan.gamelistmanager.retroarchinfoloader.domain.data

/**
 * Data class representing core info located at %RetroArchDir%/info.
 */
data class RetroArchInfoData(
    val filename: String,
    val displayName: String,
    val categories: String,
    val authors: String,
    val coreName: String,
    val supportedExtensions: String,
    val license: String,
    val permissions: String,
    val version: String,
    val database: String,
    val manufacturer: String,
    val systemName: String,
    val systemId: String,
    val notes: String,
    val description: String,
    val cheats: String,
    val saveStates: String
)