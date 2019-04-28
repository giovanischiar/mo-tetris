package io.schiar.giovani.motetris.model

import io.schiar.giovani.motetris.model.TetraminoTypes.*
import io.schiar.giovani.motetris.util.ColorBit

class Tetramino(val type: TetraminoTypes, private val color: Int) {

    companion object { const val TETRAMINO_BLOCK_QTD = 4 }

    var shape = listOf<MutableSet<ColorBit>>()

    init {
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
        for (colorBitSet in shape) {
            if (colorBitSet.size > width) {
                width = colorBitSet.size
            }
        }
        return width
    }

    private fun buildI() {
        var i = 0
        while(true) {
            if (i == TETRAMINO_BLOCK_QTD) break
            val colorBitSet = mutableSetOf<ColorBit>()
            colorBitSet.add(ColorBit(0, color))
            shape = listOf(*(shape.toTypedArray()), colorBitSet)
            i++
        }
    }

    private fun buildL() {
        var i = 0
        while(true) {
            if (i == TETRAMINO_BLOCK_QTD-1) break
            val colorBitSet = mutableSetOf<ColorBit>()
            colorBitSet.add(ColorBit(0, color))
            if (i == 2) {
                colorBitSet.add(ColorBit(1, color))
            }
            shape = listOf(*(shape.toTypedArray()), colorBitSet)
            i++
        }
    }

    private fun buildO() {
        var i = 0
        while(true) {
            if (i == TETRAMINO_BLOCK_QTD-2) break
            val colorBitSet = mutableSetOf<ColorBit>()
            if (i == 0 || i == 1) {
                colorBitSet.add(ColorBit(0, color))
                colorBitSet.add(ColorBit(1, color))
            }
            shape = listOf(*(shape.toTypedArray()), colorBitSet)
            i++
        }
    }

    private fun buildS() {
        var i = 0
        while(true) {
            if (i == TETRAMINO_BLOCK_QTD-1) break
            val colorBitSet = mutableSetOf<ColorBit>()
            when (i) {
                0 -> colorBitSet.add(ColorBit(i, color))
                1 -> {
                    colorBitSet.add(ColorBit(0, color))
                    colorBitSet.add(ColorBit(1, color))
                }
                2 -> colorBitSet.add(ColorBit(1, color))
            }
            shape = listOf(*(shape.toTypedArray()), colorBitSet)
            i++
        }
    }

    private fun buildT() {
        var i = 0
        while(true) {
            if (i == TETRAMINO_BLOCK_QTD-2) break
            val colorBitSet = mutableSetOf<ColorBit>()
            if (i == 0) {
                colorBitSet.add(ColorBit(0, color))
                colorBitSet.add(ColorBit(1, color))
                colorBitSet.add(ColorBit(2, color))
            } else if (i == 1) {
                colorBitSet.add(ColorBit(1, color))
            }
            shape = listOf(*(shape.toTypedArray()), colorBitSet)
            i++
        }
    }

}
