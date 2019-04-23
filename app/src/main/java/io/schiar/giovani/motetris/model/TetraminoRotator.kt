package io.schiar.giovani.motetris.model

import java.util.*

class TetraminoRotator(val tetramino: Tetramino) {
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

//    fun rotacionarMatrizAntiHorario(matrix: List<BitSet>): List<BitSet> {
//        val largura = matrix.size
//        val altura = matrix[0].size
//        val ret = Array(altura) { IntArray(largura) }
//        for (i in 0 until altura) {
//            for (j in 0 until largura) {
//                ret[i][j] = matrix[j][altura - i - 1]
//            }
//        }
//        return ret
//    }
}