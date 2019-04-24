package io.schiar.giovani.motetris

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.schiar.giovani.motetris.model.Game
import io.schiar.giovani.motetris.model.Position
import io.schiar.giovani.motetris.model.Resolution
import java.util.*

class GameViewModel : ViewModel(), OnChangeGameListener {
    private lateinit var onInputListener: OnInputListener

    private val gameLiveData: MutableLiveData<Game> by lazy {
        MutableLiveData<Game>()
    }

    val bitSets: LiveData<List<BitSet>> = Transformations.map(gameLiveData) {
        game -> game.board.lines
    }

    val nextContent: MutableLiveData<List<BitSet>> by lazy {
        MutableLiveData<List<BitSet>>()
    }

    val score: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }


    fun startGame(x: Int, y: Int, width: Int, height: Int)  {
        val game = Game(Resolution(width, height), Position(x, y), this)
        game.addTetraminoOnBoard()
        onInputListener = game
        Thread(game).start()
    }

    override fun updateNextTetramino(nextTetramino: List<BitSet>) {
        this.nextContent.postValue(nextTetramino)
    }

    override fun updateScore(score: Int) {
        this.score.postValue(score)
    }

    override fun updateGameState(game: Game) {
        gameLiveData.postValue(game)
    }

    fun leftClicked() {
        onInputListener.moveTetraminoLeft()
    }

    fun rightClicked() {
        onInputListener.moveTetraminoRight()
    }

    fun upClicked() {
        onInputListener.rotateTetraminoClockwise()
    }

    fun downClicked() {
        onInputListener.moveTetraminoDown()
    }
 }
