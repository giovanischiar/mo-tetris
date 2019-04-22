package io.schiar.giovani.motetris.model

import io.schiar.giovani.motetris.*
import java.util.*

class Board(val resolution: Resolution) {
    var lines: List<BitSet>

    init {
        lines = listOf()
        for (i in 0 until resolution.height) {
            val bitSet = BitSet()
            lines = listOf(bitSet, *(lines.toTypedArray()))
        }

        lines[resolution.height-1].set(5)
    }

    fun bitsetsCollidesBit(bitSets: List<BitSet>, nextPosition: Position): Boolean {
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