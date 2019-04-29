package io.schiar.giovani.motetris

import io.schiar.giovani.motetris.model.Board
import io.schiar.giovani.motetris.model.Position
import io.schiar.giovani.motetris.model.Resolution
import io.schiar.giovani.motetris.util.ColorBit
import io.schiar.giovani.motetris.util.get
import junit.framework.Assert.assertEquals
import org.junit.Test

class BoardTest {
    @Test
    fun addColorBitsOnBoardTest() {
        val board = Board(Resolution(4, 4))
        val line = mutableSetOf(ColorBit(0, 0), ColorBit(1, 0))

        board.addColorBitsOnBoard(listOf(line, line), Position(1, 1))

        assertEquals(1, board.lines[1][1]?.getIndex())
        assertEquals(2, board.lines[1][2]?.getIndex())

        assertEquals(1, board.lines[2][1]?.getIndex())
        assertEquals(2, board.lines[2][2]?.getIndex())
    }
}