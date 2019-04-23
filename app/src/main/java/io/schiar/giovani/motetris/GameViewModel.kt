package io.schiar.giovani.motetris

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel;
import io.schiar.giovani.motetris.model.*
import java.util.*

class GameViewModel : ViewModel() {
    private val gameLiveData: MutableLiveData<Game> by lazy {
        MutableLiveData<Game>()
    }

    val bitsets: LiveData<List<BitSet>> = Transformations.map(gameLiveData) {
        game -> game.board.lines
    }

    val isPaused: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private fun pause() {
        val game = gameLiveData.value ?: return
        game.pause()
        gameLiveData.postValue(game)
    }

    fun startGame(x: Int, y: Int, width: Int, height: Int)  {
        val game = Game(Resolution(width, height), Position(x, y))
        initializeGame(game)
    }

    private fun initializeGame(game: Game) {
        game.generateTetramino()
        game.start()
        gameLiveData.postValue(game)
        isPaused.postValue(game.pause)
    }

    fun nextState() {
        val game = gameLiveData.value ?: return
        game.moveTetraminoDown()
        gameLiveData.postValue(game)
    }

    fun leftClicked() {
        val game = gameLiveData.value ?: return
        game.moveTetraminoLeft()
        gameLiveData.postValue(game)
    }

    fun rightClicked() {
        val game = gameLiveData.value ?: return
        game.moveTetraminoRight()
        gameLiveData.postValue(game)
    }

    fun upClicked() {
        val game = gameLiveData.value ?: return
        game.rotateTetraminoClockwise()
        gameLiveData.postValue(game)
    }

    fun downClicked() {
        nextState()
    }
 }
