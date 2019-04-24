package io.schiar.giovani.motetris.model

import java.util.*

class TetraminoFetcher {

    private val types = TetraminoTypes.values().toList()
    private val buffer: LinkedList<TetraminoTypes> = LinkedList()

    init {
        buffer.add(types.shuffled().first())
        buffer.add(types.shuffled().first())
    }

    fun nextTetramino(): Tetramino {
        val tetramino = buffer.pollLast()
        buffer.addFirst(types.shuffled().first())
        return Tetramino(tetramino)
    }

    fun next(): TetraminoTypes {
        return buffer.last
    }

}
