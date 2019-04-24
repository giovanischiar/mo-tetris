package io.schiar.giovani.motetris

import java.util.*

operator fun BitSet.times(other: BitSet): BitSet {
    val newB1 = BitSet()
    newB1.or(this)
    newB1.and(other)
    return newB1
}

fun BitSet.orWithOffset(other: BitSet, offset: Int = 0) {
    if (offset < 0) {
        return
    }
    val newB1 = BitSet()
    for(i in 0 until other.length()) {
        newB1.set(i+offset, other[i])
    }
    or(newB1)
}

fun BitSet.xorWithOffset(other: BitSet, offset: Int = 0) {
    if (offset < 0) {
        return
    }
    val newB1 = BitSet()
    for(i in 0 until other.length()) {
        newB1.set(i+offset, other[i])
    }
    xor(newB1)
}
