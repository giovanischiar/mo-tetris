package io.schiar.giovani.motetris

import io.schiar.giovani.motetris.model.Game
import io.schiar.giovani.motetris.util.ColorBit

interface OnChangeGameListener {
    fun updateGameState(game: Game)
    fun updateNextTetramino(nextTetramino: List<Set<ColorBit>>)
    fun updateScore(score: String)
    fun updateResolutions(width: Int, height: Int)
}

interface OnInputListener {
    fun moveTetraminoLeft()
    fun moveTetraminoRight()
    fun moveTetraminoDown()
    fun rotateTetraminoClockwise()
}

interface OnBoardChangeListener {
    fun onBoardChange()
}