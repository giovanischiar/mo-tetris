package io.schiar.giovani.motetris

import java.util.*

class TetraminoFetcher {
    private val types = listOf('i', 'o', 's', 't', 'l')
    private val buffer: LinkedList<Char> = LinkedList()

    init {
        buffer.add(types.shuffled().first())
        buffer.add(types.shuffled().first())
    }

    fun nextTetramino(): Tetramino {
        val tetramino = buffer.pollLast()
        buffer.addFirst(types.shuffled().first())
        return Tetramino(tetramino)
    }

    fun next(): Char {
        return buffer.last
    }
}
