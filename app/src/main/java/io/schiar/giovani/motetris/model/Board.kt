package io.schiar.giovani.motetris.model

import io.schiar.giovani.motetris.truePart
import io.schiar.giovani.motetris.trueSize
import io.schiar.giovani.motetris.updated
import io.schiar.giovani.motetris.withTrueSize
import java.util.*

class Board(resolution: Resolution) {
    var lines: List<BitSet>

    init {
        lines = listOf()
        for (i in 0 until resolution.height) {
            val bitSet = BitSet().withTrueSize(resolution.width)
            lines = listOf(bitSet, *(lines.toTypedArray()))
        }

        lines[resolution.height-1].set(5)
    }

    fun yOverflowsBoard(y: Int): Boolean {
        return y >= lines.size
    }

    fun xOverflowsBoard(x: Int): Boolean {
        return x >= lines[0].trueSize()
    }

    fun bitsetsCollidesBit(bitSets: List<BitSet>, nextPosition: Position): Boolean {
        var currentY = nextPosition.y
        for ((i, bitSet) in bitSets.withIndex()) {
            if (yOverflowsBoard(currentY)) {
                return true
            }
            for (j in 0 until bitSet.length()) {
                val currentX = nextPosition.x + j
                if (bitSet[j]) {
                    if (xOverflowsBoard(currentX)) {
                        return true
                    }

                    if (lines[currentY][currentX]) {
                        if (i+1 == bitSets.size) {
                            return true
                        }

                        if (!bitSets[i+1][j]) {
                            return true
                        }

                    }
                }
            }
            currentY++
        }
        return false
    }

    fun changeBitValue(x: Int, y: Int, value: Boolean = true) {
        lines[y].set(x, value)
    }

    fun remBitSetsOnBoard(bitSets: List<BitSet>, position: Position): Boolean {
        if (lines.size < (position.y + bitSets.size)) {
            return false
        }

        for ((linesIndex, boardBitSet) in lines.withIndex()) {
            if (linesIndex == position.y) {
                var newLinesIndex = linesIndex
                for (bitSetIndex in 0 until bitSets.size) {
                    val bitSet = bitSets[bitSetIndex]
                    val newLine = BitSet().withTrueSize(boardBitSet.trueSize())
                    for (i in 0 until bitSet.length()) {
                        val x = position.x + i
                        newLine.set(x, x+1, bitSet[i])
                    }
                    lines[newLinesIndex].xor(newLine.truePart())
                    newLinesIndex++
                }
            }
        }
        return true
    }

    fun addBitSetsOnBoard(bitSets: List<BitSet>, position: Position): Boolean {
        if (lines.size < (position.y + bitSets.size)) {
             return false
        }

        for ((linesIndex, boardBitSet) in lines.withIndex()) {
            if (linesIndex == position.y) {
                var newLinesIndex = linesIndex
                for (bitSetIndex in 0 until bitSets.size) {
                    val bitSet = bitSets[bitSetIndex]
                    val newLine = BitSet().withTrueSize(boardBitSet.trueSize())
                    for (i in 0 until bitSet.length()) {
                        val x = position.x + i
                        newLine.set(x, x+1, bitSet[i])
                    }
                    lines[newLinesIndex].or(newLine)
                    newLinesIndex++
                }
            }
        }

        return true
    }
}