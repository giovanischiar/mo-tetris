package io.schiar.giovani.motetris

import io.schiar.giovani.motetris.model.Game

interface OnChangeGameListener {
    fun changeGameState(game: Game)
}

interface OnInputListener {
    fun moveTetraminoLeft()
    fun moveTetraminoRight()
    fun moveTetraminoDown()
    fun rotateTetraminoClockwise()
}