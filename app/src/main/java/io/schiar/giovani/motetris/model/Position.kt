package io.schiar.giovani.motetris.model

class Position(var x: Int, var y: Int) {

    fun moveY(value: Int = 1) {
        y += value
    }

    fun moveX(value: Int = 1) {
        x += value
    }

    fun copy(): Position {
        return Position(x, y)
    }
}