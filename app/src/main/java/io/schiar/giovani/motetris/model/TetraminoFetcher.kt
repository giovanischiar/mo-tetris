package io.schiar.giovani.motetris.model

import java.util.*

class TetraminoFetcher(private val tetraminoColors: Map<TetraminoTypes, Int>) {

    private val types = TetraminoTypes.values().toList()
    private val buffer: LinkedList<TetraminoTypes> = LinkedList()

    init {
        buffer.add(types.shuffled().first())
        buffer.add(types.shuffled().first())
    }

    fun nextTetramino(): Tetramino {
        val tetramino = buffer.pollLast()
        buffer.addFirst(types.shuffled().first())
        return Tetramino(tetramino, tetraminoColors[tetramino] ?: 0)
    }

    fun next(): Tetramino {
        return Tetramino(buffer.last, tetraminoColors[buffer.last] ?: 0)
    }

}
