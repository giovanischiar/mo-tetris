package io.schiar.giovani.motetris.model

import io.schiar.giovani.motetris.OnBoardChangeListener

class BoardFetcher {

    fun fetch(resolution: Resolution, onBoardChangeListener: OnBoardChangeListener): Board {
//        return Board(
//            arrayOf(
//                arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
//                arrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0))
//        )

        return Board(resolution, onBoardChangeListener)
    }
}