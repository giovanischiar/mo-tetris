package io.schiar.giovani.motetris.model

import android.util.Log
import java.util.BitSet
import io.schiar.giovani.motetris.model.TetraminoTypes.*

class Tetramino(type: TetraminoTypes) {
    companion object { const val TETRAMINO_BLOCK_QTD = 4 }

    var shape = listOf<BitSet>()

    init {
        Log.d("motetris", type.toString())
        when(type) {
            I -> buildI()
            L -> buildL()
            O -> buildO()
            S -> buildS()
            T -> buildT()
        }
    }

    fun width(): Int {
        var width = 0
        for (bitSet in shape) {
            if (bitSet.length() > width) {
                width = bitSet.length()
            }
        }
        return width
    }

    fun rearEndPosition(position: Position): Position {
        return Position(position.x + width(), position.y + shape.size )
    }

    private fun buildI() {
        var i = 0
        while(true) {
            if (i == TETRAMINO_BLOCK_QTD) break
            val bitSet = BitSet(TETRAMINO_BLOCK_QTD)
            bitSet.set(0)
            shape = listOf(*(shape.toTypedArray()), bitSet)
            i++
        }
    }

    private fun buildL() {
        var i = 0
        while(true) {
            if (i == TETRAMINO_BLOCK_QTD-1) break
            val bitSet = BitSet(TETRAMINO_BLOCK_QTD)
            bitSet.set(0)
            if (i == 2) {
                bitSet.set(1)
            }
            shape = listOf(*(shape.toTypedArray()), bitSet)
            i++
        }
    }

    private fun buildO() {
        var i = 0
        while(true) {
            if (i == TETRAMINO_BLOCK_QTD-2) break
            val bitSet = BitSet(TETRAMINO_BLOCK_QTD)
            if (i == 0 || i == 1) {
                bitSet.set(0)
                bitSet.set(1)
            }
            shape = listOf(*(shape.toTypedArray()), bitSet)
            i++
        }
    }

    private fun buildS() {
        var i = 0
        while(true) {
            if (i == TETRAMINO_BLOCK_QTD-1) break
            val bitSet = BitSet(TETRAMINO_BLOCK_QTD)
            when (i) {
                0 -> bitSet.set(0)
                1 -> {
                    bitSet.set(0)
                    bitSet.set(1)
                }
                2 -> bitSet.set(1)
            }
            shape = listOf(*(shape.toTypedArray()), bitSet)
            i++
        }
    }

    private fun buildT() {
        var i = 0
        while(true) {
            if (i == TETRAMINO_BLOCK_QTD-2) break
            val bitSet = BitSet(TETRAMINO_BLOCK_QTD)
            if (i == 0) {
                bitSet.set(0)
                bitSet.set(1)
                bitSet.set(2)
            } else if (i == 1) {
                bitSet.set(1)
            }
            shape = listOf(*(shape.toTypedArray()), bitSet)
            i++
        }
    }
}
