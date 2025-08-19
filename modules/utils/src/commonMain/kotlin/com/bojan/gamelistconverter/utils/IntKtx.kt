package com.bojan.gamelistconverter.utils

fun Int.exitCodeOk(): Boolean = when (this) {
    0, -1 -> true
    else -> false
}