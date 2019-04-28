package io.schiar.giovani.motetris.util

class ColorBit(index: Int, color: Int) {

    /**
     *    0000000 00000000 00000000 00000000
     *     index    red     green     blue
     *
     */

    private var colorBit = 0

    init {
        val colorShifted = extractColor(color)
        val indexShifted = shiftIndex(index)
        colorBit = indexShifted or colorShifted
    }

    override fun toString(): String {
        return "index: ${getIndex()} color: ${Integer.toBinaryString(getColor())}"
    }

    fun getColor(): Int {
        return colorBit xor 0x7F000000
    }

    fun getIndex(): Int {
        val index = colorBit and 0x7F000000
        return index ushr 24
    }

    fun setIndex(index: Int) {
        val indexShifted = shiftIndex(index)
        colorBit = colorBit and 0x00FFFFFF
        colorBit = indexShifted or colorBit
    }

    fun addIndexOffSet(offSet: Int) {
        val index = getIndex()+offSet
        val indexShifted = shiftIndex(index)
        colorBit = colorBit and 0x00FFFFFF
        colorBit = indexShifted or colorBit
    }

    private fun extractColor(color: Int): Int {
        return color and 0x00FFFFFF
    }

    private fun shiftIndex(index: Int): Int {
        return index shl 24
    }

}