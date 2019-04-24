package io.schiar.giovani.motetris.model

import java.util.*

class TetraminoFlipper(private val tetramino: Tetramino) {

    fun rotateAntiClockWise(): List<BitSet> {
        val matrix = tetramino.shape
        val width = matrix.size
        val height = tetramino.width()

        var ret = listOf<BitSet>()
        for (i in 0 until height) {
            ret = listOf(*(ret.toTypedArray()), BitSet())
        }
        for (i in 0 until height) {
            for (j in 0 until width) {
                ret[i][j] = matrix[width - j - 1][i]
            }
        }
        return ret
    }

    fun rotateClockWise(): List<BitSet> {
        val matrix = tetramino.shape
        val width = matrix.size
        val height = tetramino.width()

        var ret = listOf<BitSet>()
        for (i in 0 until height) {
            ret = listOf(*(ret.toTypedArray()), BitSet())
        }
        for (i in 0 until height) {
            for (j in 0 until width) {
                ret[i][j] = matrix[j][height - i - 1]
            }
        }
        return ret
    }

}