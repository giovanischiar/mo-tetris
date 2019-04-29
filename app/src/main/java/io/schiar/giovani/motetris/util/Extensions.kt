package io.schiar.giovani.motetris.util

operator fun MutableSet<ColorBit>.get(i: Int): ColorBit? {
    val indexSorted = this.filter { it.getIndex() == i }
    if (indexSorted.isNotEmpty()) {
        return indexSorted[0]
    }
    return null
}

operator fun MutableSet<ColorBit>.set(i: Int, colorBit: ColorBit) {
    val newColorBit = ColorBit(colorBit.getIndex(), colorBit.getColor())
    newColorBit.setIndex(i)
    val indexSorted = this.filter { it.getIndex() == i }
    if (indexSorted.isNotEmpty()) {
        this.remove(indexSorted[0])
    }

    this.add(newColorBit)
}

fun MutableSet<ColorBit>.mergeWithOffset(other: MutableSet<ColorBit>, offset: Int = 0) {
    if (offset < 0) {
        return
    }

    for (otherColorBit in other) {
        val newColorBit = ColorBit(otherColorBit.getIndex(), otherColorBit.getColor())
        newColorBit.addIndexOffSet(offset)
        val colorBitsSameIndex = this.filter { it.getIndex() == newColorBit.getIndex() }
        if (colorBitsSameIndex.isNotEmpty()) {
            this.remove(colorBitsSameIndex[0])
        }
        this.add(newColorBit)
    }
}

fun MutableSet<ColorBit>.unMergeWithOffset(other: MutableSet<ColorBit>, offset: Int = 0) {
    if (offset < 0) {
        return
    }

    for (otherColorBit in other) {
        val newColorBit = ColorBit(otherColorBit.getIndex(), otherColorBit.getColor())
        newColorBit.addIndexOffSet(offset)
        val colorBitsSameIndex = this.filter { it.getIndex() == newColorBit.getIndex() }
        if (colorBitsSameIndex.isNotEmpty()) {
            this.remove(colorBitsSameIndex[0])
        }
    }
}

fun MutableSet<ColorBit>.intersects(other: MutableSet<ColorBit>):Boolean {
    for (otherColorBit in other) {
        val colorBitsSameIndex = this.filter { it.getIndex() == otherColorBit.getIndex() }
        if (colorBitsSameIndex.isNotEmpty()) {
            return true
        }
    }

    return false
}

fun MutableSet<ColorBit>.nextClearBit(index: Int = 0): Int {
    val colorBitsOrdered = this.map { it.getIndex() }.sorted()
    if (colorBitsOrdered.isEmpty()) {
        return index
    }
    for (i in index..colorBitsOrdered.last()) {
        if (this.none { it.getIndex() == i }) {
            return i
        }
    }
    return colorBitsOrdered.last()+1
}

fun Array<Array<Int>>.toListColorBitSet(): List<MutableSet<ColorBit>> {
    this.reverse()
    var listColorBitSet = listOf<MutableSet<ColorBit>>()
    val height = this.size
    for (i in 0 until height) {
        val colorBitSet = this[i].mapIndexed{ j, int ->
            ColorBit(if (int != 0) j else -1, 0x7FFFFF00)
        }.filter { it.getIndex() != -1 }.toHashSet()
        listColorBitSet = listOf(colorBitSet, *(listColorBitSet.toTypedArray()))
    }
    return listColorBitSet
}