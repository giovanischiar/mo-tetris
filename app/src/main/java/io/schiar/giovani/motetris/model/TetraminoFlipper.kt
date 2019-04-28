package io.schiar.giovani.motetris.model

import io.schiar.giovani.motetris.util.ColorBit
import io.schiar.giovani.motetris.util.get
import io.schiar.giovani.motetris.util.set

class TetraminoFlipper(private val tetramino: Tetramino) {

    fun rotateAntiClockWise(): List<MutableSet<ColorBit>> {
        val matrix = tetramino.shape
        val width = matrix.size
        val height = tetramino.width()

        var ret = listOf<MutableSet<ColorBit>>()
        for (i in 0 until height) {
            ret = listOf(*(ret.toTypedArray()), mutableSetOf())
        }
        for (i in 0 until height) {
            for (j in 0 until width) {
                ret[i][j] = matrix[width - j - 1][i] ?: continue
            }
        }
        return ret
    }

    fun rotateClockWise(): List<MutableSet<ColorBit>> {
        val matrix = tetramino.shape
        val width = matrix.size
        val height = tetramino.width()

        var ret = listOf<MutableSet<ColorBit>>()
        for (i in 0 until height) {
            ret = listOf(*(ret.toTypedArray()), mutableSetOf())
        }
        for (i in 0 until height) {
            for (j in 0 until width) {
                ret[i][j] = matrix[j][height - i - 1] ?: continue
            }
        }
        return ret
    }

}