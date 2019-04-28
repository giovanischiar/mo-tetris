package io.schiar.giovani.motetris

import io.schiar.giovani.motetris.util.ColorBit
import io.schiar.giovani.motetris.util.get
import io.schiar.giovani.motetris.util.mergeWithOffset
import io.schiar.giovani.motetris.util.nextClearBit
import junit.framework.Assert.assertEquals
import org.junit.Test

class ColorBitSetTest {

    @Test
    fun verifyMerge() {
        val line  = mutableSetOf<ColorBit>()
        val setToMerge = mutableSetOf(
            ColorBit(0, 0),
            ColorBit(1, 0)
        )

        line.mergeWithOffset(setToMerge, 1)
        line.toList()

        assert(line[2] != null)
        assert(line[1] != null)
    }

    @Test
    fun verifyNextClearBit() {
        val setToMerge = mutableSetOf(
            ColorBit(0, 0),
            ColorBit(1, 0),
            ColorBit(2, 0),
            ColorBit(3, 0),
            ColorBit(4, 0),
            ColorBit(5, 0),
            ColorBit(6, 0)
        )

        assertEquals(7, setToMerge.nextClearBit())
    }

    @Test
    fun verifyNextClearBit2() {
        val setToMerge = mutableSetOf(
            ColorBit(0, 0),
            ColorBit(2, 0),
            ColorBit(3, 0),
            ColorBit(5, 0),
            ColorBit(6, 0)
        )

        assertEquals(1, setToMerge.nextClearBit(0))
    }

    @Test
    fun verifyNextClearBit3() {
        val setToMerge = mutableSetOf(
            ColorBit(0, 0),
            ColorBit(2, 0),
            ColorBit(3, 0),
            ColorBit(5, 0),
            ColorBit(6, 0)
        )

        assertEquals(4, setToMerge.nextClearBit(2))
    }

    @Test
    fun verifyNextClearBit4() {
        val setToMerge = mutableSetOf(
            ColorBit(0, 0),
            ColorBit(2, 0),
            ColorBit(3, 0),
            ColorBit(5, 0),
            ColorBit(6, 0)
        )

        assertEquals(4, setToMerge.nextClearBit(3))
    }

    @Test
    fun verifyNextClearBit5() {
        val setToMerge = mutableSetOf(
            ColorBit(0, 0),
            ColorBit(2, 0),
            ColorBit(3, 0),
            ColorBit(5, 0),
            ColorBit(6, 0)
        )

        assertEquals(7, setToMerge.nextClearBit(5))
    }

    @Test
    fun verifyNextClearBit6() {
        val setToMerge = mutableSetOf<ColorBit>()
        assertEquals(7, setToMerge.nextClearBit(7))
    }
}