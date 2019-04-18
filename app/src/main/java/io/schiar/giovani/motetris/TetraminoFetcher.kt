package io.schiar.giovani.motetris

class TetraminoFetcher {
    fun nextTetramino(): Tetramino {
        val types = listOf('i', 'o', 's', 't', 'l')
        return Tetramino(types.shuffled().first())
    }
}
