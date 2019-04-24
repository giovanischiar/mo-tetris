package io.schiar.giovani.motetris.model

import io.schiar.giovani.motetris.orWithOffset
import io.schiar.giovani.motetris.times
import io.schiar.giovani.motetris.xorWithOffset
import java.util.*

class Board(private val resolution: Resolution) {

    var lines: List<BitSet>
    var linesRemoved = 0

    init {
        lines = listOf()
        for (i in 0 until resolution.height) {
            val bitSet = BitSet()
            lines = listOf(bitSet, *(lines.toTypedArray()))
        }
    }

    fun removeFullLinesAndUpdateBoard(): Boolean {
        var linesWasRemoved = false
        for ((i, bitSet) in lines.withIndex()) {
            if (bitSet.nextClearBit(0) >= resolution.width) {
                lines = listOf(
                    BitSet(),
                    *(lines.subList(0, i).toTypedArray()),
                    *(lines.subList(i+1, lines.size)).toTypedArray()
                )
                linesRemoved++
                linesWasRemoved = true
            }
        }
        return linesWasRemoved
    }

    fun verifyBitSetsCollision(bitSets: List<BitSet>, nextPosition: Position): Boolean {
        if (resolution.height < (nextPosition.y + bitSets.size)) {
            return true
        }

        if(nextPosition.x < 0) {
            return true
        }

        var nextY = nextPosition.y
        for (bitSet in bitSets) {
            if (resolution.width < (nextPosition.x + bitSet.length())) {
                return true
            }
            val newLine = BitSet()
            newLine.orWithOffset(bitSet, nextPosition.x)
            if (!(lines[nextY] * newLine).isEmpty) {
                return true
            }
            nextY++
        }
        return false
    }

    fun remBitSetsOnBoard(bitSets: List<BitSet>, position: Position): Boolean {
        var newY = position.y
        for (bitSet in bitSets) {
            lines[newY].xorWithOffset(bitSet, position.x)
            newY++
        }
        return true
    }

    fun addBitSetsOnBoard(bitSets: List<BitSet>, position: Position) {
        var newY = position.y
        for (bitSet in bitSets) {
            lines[newY].orWithOffset(bitSet, position.x)
            newY++
        }
    }

}