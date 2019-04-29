package io.schiar.giovani.motetris

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.schiar.giovani.motetris.model.Game
import io.schiar.giovani.motetris.model.Resolution
import io.schiar.giovani.motetris.model.TetraminoTypes
import io.schiar.giovani.motetris.model.TetraminoTypes.*
import io.schiar.giovani.motetris.util.ColorBit

class GameViewModel : ViewModel(), OnChangeGameListener {

    private lateinit var onInputListener: OnInputListener

    private val gameLiveData: MutableLiveData<Game> by lazy { MutableLiveData<Game>() }

    val colorBitSets: LiveData<List<Set<ColorBit>>> = Transformations.map(gameLiveData) { game -> game.board.lines }

    val nextContent: MutableLiveData<List<Set<ColorBit>>> by lazy { MutableLiveData<List<Set<ColorBit>>>() }

    val score = MutableLiveData<String>().apply { value = "0" }

    val resolutionWidth = MutableLiveData<Int>().apply { value = 10 }

    val resolutionHeight = MutableLiveData<Int>().apply { value = 17 }

    private val tetraminoColors = MutableLiveData<Map<TetraminoTypes, Int>>().apply { value = mapOf(
        I to 0xF44366, L to 0x9C27B0,
        O to 0x2196F3, S to 0x4CAF50,
        T to 0xFFEB3B
    ) }

    fun startGame()  {
        val width = resolutionWidth.value ?: return
        val height = resolutionHeight.value ?: return
        val tetraminoColors = tetraminoColors.value ?: return

        val resolution = Resolution(width, height)
        val game = Game(resolution, tetraminoColors, this)
        game.addTetraminoOnBoard()
        onInputListener = game
        Thread(game).start()
    }

    override fun updateNextTetramino(nextTetramino: List<Set<ColorBit>>) {
        this.nextContent.postValue(nextTetramino)
    }

    override fun updateScore(score: String) {
        this.score.postValue(score)
    }

    override fun updateGameState(game: Game) {
        gameLiveData.postValue(game)
    }

    override fun updateResolutions(width: Int, height: Int) {
        resolutionWidth.postValue(width)
        resolutionHeight.postValue(height)
    }

    fun leftClicked() { onInputListener.moveTetraminoLeft() }

    fun rightClicked() { onInputListener.moveTetraminoRight() }

    fun upClicked() { onInputListener.rotateTetraminoClockwise() }

    fun downClicked() { onInputListener.moveTetraminoDown() }

 }
