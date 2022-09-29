package com.smallgames.match.utils

fun intToTime(time: Int): String {
    val milliseconds = time % 100
    val seconds = time / 100 % 60
    val minutes = time / 100 / 60
    return String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
}