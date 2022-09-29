package com.smallgames.match.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smallgames.match.data.Storage
import com.smallgames.match.model.FruitModel
import com.smallgames.match.utils.Fruits
import com.smallgames.match.utils.GameStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MatchViewModel(private val storage: Storage) : ViewModel() {

    private lateinit var timerJob: Job

    private var _time by mutableStateOf(0)
    val time
        get() = _time

    private var _bestTime by mutableStateOf(storage.getBestTime())
    val bestTime
        get() = _bestTime

    private val _fruits = mutableStateListOf<FruitModel>()
    val fruits
        get() = _fruits

    private var _gameStatus by mutableStateOf(GameStatus.GameInProgress)
    val gameStatus
        get() = _gameStatus

    private var matchesFound = 0

    private var selectedCardId: Int = -1

    init {
        initFruits()
    }

    fun onCardClick(cardId: Int) = viewModelScope.launch {
        changeCardSide(cardId,true)
        if(time == 0) {
            startTimer()
        }
        if (selectedCardId == -1) {
            selectedCardId = cardId
        } else {
            if (isFruitsEqualByCardId(cardId, selectedCardId)) {
                matchesFound += 1
                if (isGameEnd())
                    endGame()
            } else {
                _gameStatus = GameStatus.ShowCards
                delay(1000)
                changeCardSide(cardId, false)
                changeCardSide(selectedCardId, false)
                _gameStatus = GameStatus.GameInProgress
            }
            selectedCardId = -1
        }

    }
    private fun startTimer() {
        timerJob = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                _time += 1
                delay(10)
            }
        }
    }

    private fun isFruitsEqualByCardId(firstCardId: Int, secondCardId: Int) =
        _fruits.find { it.id == firstCardId }!!.fruit  == _fruits.find { it.id == secondCardId }!!.fruit


    private fun changeCardSide(cardId: Int, showFruit: Boolean) {
        val index = _fruits.indexOf(_fruits.find { it.id == cardId })
        _fruits[index] = _fruits[index].copy(showFruit = showFruit)
    }

    private fun isGameEnd(): Boolean = matchesFound == 12

    private fun endGame() {
        timerJob.cancel()
        changeBestScore()
        _gameStatus = GameStatus.EndGame
    }

    private fun changeBestScore() {
        val bestTime = storage.getBestTime()
        if(time < bestTime) {
            _bestTime = time
            storage.setBestTime(time)
        }
    }

    fun restartGame() {
        _fruits.clear()
        initFruits()
        matchesFound = 0
        _time = 0
        _gameStatus = GameStatus.GameInProgress
    }

    private fun getRandomFruits(): List<Fruits> = Fruits.values().toList().shuffled().subList(0,12)

    private fun initFruits() {
        var id = 0
        getRandomFruits().forEach {
            _fruits.apply {
                add (
                    FruitModel(
                        id = id++,
                        fruit = it
                    ))
                add (
                    FruitModel(
                        id = id++,
                        fruit = it
                    ))
            }
        }
        _fruits.shuffle()
    }



}