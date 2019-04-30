package io.schiar.giovani.motetris.model

import io.schiar.giovani.motetris.OnBoardChangeListener
import io.schiar.giovani.motetris.util.*

class Board(val resolution: Resolution, private val onBoardChangeListener: OnBoardChangeListener) {

    var lines: List<Set<ColorBit>>
    var linesRemoved = 0

    init {
        lines = listOf()
        for (i in 0 until resolution.height) {
            val colorBitSet = setOf<ColorBit>()
            lines = listOf(colorBitSet, *(lines.toTypedArray()))
        }
    }

    constructor(intMatrix: Array<Array<Int>>, onBoardChangeListener: OnBoardChangeListener)
            : this(Resolution(intMatrix[0].size, intMatrix.size), onBoardChangeListener) {
        lines = intMatrix.toListColorBitSet()
    }

    fun addColorBitsOnBoard(colorBitSets: List<Set<ColorBit>>, position: Position) {
        var newY = position.y
        for (colorBit in colorBitSets) {
            if (newY < 0) continue
            val newLine = mutableSetOf<ColorBit>()
            newLine.mergeWithOffset(lines[newY])
            newLine.mergeWithOffset(colorBit, position.x)
            addLine(newY, newLine)
            newY++
        }
    }

    fun remColorBitsOnBoard(colorBitSets: List<Set<ColorBit>>, position: Position): Boolean {
        var newY = position.y
        for (bitSet in colorBitSets) {
            val newLine = mutableSetOf<ColorBit>()
            newLine.mergeWithOffset(lines[newY])
            newLine.unMergeWithOffset(bitSet, position.x)
            addLine(newY, newLine)
            newY++
        }
        return true
    }

    fun removeFullLinesAndUpdateBoard(): Boolean {
        var linesWasRemoved = false
        for ((i, colorBitSet) in lines.withIndex()) {
            if (colorBitSet.nextClearBit() >= resolution.width) {
                removeFullLine(i)
                linesWasRemoved = true
            }
        }
        return linesWasRemoved
    }

    private fun removeFullLine(i: Int) {
        lines = listOf(
            mutableSetOf(),
            *(lines.subList(0, i).toTypedArray()),
            *(lines.subList(i+1, lines.size)).toTypedArray()
        )
        linesRemoved++
        onBoardChangeListener.onBoardChange()
    }

    private fun addLine(i: Int, line: Set<ColorBit>) {
        lines = listOf(
            *(lines.subList(0, i).toTypedArray()),
            line,
            *(lines.subList(i+1, lines.size)).toTypedArray()
        )
        onBoardChangeListener.onBoardChange()
    }

    fun verifyColorBitSetsCollision(colorBitSets: List<Set<ColorBit>>, nextPosition: Position): Boolean {
        if (resolution.height < (nextPosition.y + colorBitSets.size)) {
            return true
        }

        if(nextPosition.x < 0) {
            return true
        }

        var nextY = nextPosition.y
        for (colorBitSet in colorBitSets) {
            if (resolution.width < (nextPosition.x + colorBitSet.size)) {
                return true
            }
            val newLine = mutableSetOf<ColorBit>()
            newLine.mergeWithOffset(colorBitSet, nextPosition.x)
            if (lines[nextY].intersects(newLine)) {
                return true
            }
            nextY++
        }
        return false
    }

}