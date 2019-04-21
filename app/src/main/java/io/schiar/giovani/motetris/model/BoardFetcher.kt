package io.schiar.giovani.motetris.model

class BoardFetcher {
    fun fetch(resolution: Resolution): Board {
        return Board(resolution)
    }
}