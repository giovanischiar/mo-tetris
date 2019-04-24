package io.schiar.giovani.motetris

import io.schiar.giovani.motetris.model.Game
import java.util.*

interface OnChangeGameListener {
    fun updateGameState(game: Game)
    fun updateNextTetramino(nextTetramino: List<BitSet>)
    fun updateScore(score: String)
}

interface OnInputListener {
    fun moveTetraminoLeft()
    fun moveTetraminoRight()
    fun moveTetraminoDown()
    fun rotateTetraminoClockwise()
}