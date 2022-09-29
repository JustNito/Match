package com.smallgames.match.data

interface Storage {

    fun getBestTime(): Int

    fun setBestTime(time: Int)
}