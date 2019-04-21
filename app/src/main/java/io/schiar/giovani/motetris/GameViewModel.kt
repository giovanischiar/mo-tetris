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

    val isPaused: LiveData<Boolean> = Transformations.map(gameLiveData) {
            game -> game.pause
    }

    private fun pause() {
        val game = gameLiveData.value ?: return
        game.pause()
        gameLiveData.postValue(game)
    }

    fun initializeBoard(x: Int, y: Int, width: Int, height: Int)  {
        val game = Game(Resolution(width, height), Position(x, y))
        start(game)
    }

    private fun start(game: Game) {
        game.generateTetramino()
        gameLiveData.postValue(game)
    }

    fun nextState() {
        val game = gameLiveData.value ?: return
        game.moveCurrentTetraminoDown()
        gameLiveData.postValue(game)
    }

    fun leftClicked() {
        val game = gameLiveData.value ?: return
        game.moveCurrentTetraminoLeft()
        gameLiveData.postValue(game)
    }

    fun rightClicked() {
        val game = gameLiveData.value ?: return
        game.moveCurrentTetraminoRight()
        gameLiveData.postValue(game)
    }

    fun downClicked() {
        nextState()
    }
 }
