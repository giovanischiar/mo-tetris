package io.schiar.giovani.motetris

import java.util.*

fun <E> Iterable<E>.updated(index: Int, elem: E) = mapIndexed { i, existing ->
    if (i == index) elem else existing
}

operator fun BitSet.plus(other: BitSet): BitSet {
    val newB1 = BitSet()
    newB1.or(this)
    newB1.or(other)
    return newB1
}

operator fun BitSet.plusAssign(other: BitSet) {
    this.or(other)
}

operator fun BitSet.times(other: BitSet): BitSet {
    val newB1 = BitSet()
    newB1.or(this)
    newB1.and(other)
    return newB1
}

operator fun BitSet.timesAssign(other: BitSet) {
    this.and(other)
}

operator fun BitSet.minus(other: BitSet): BitSet {
    val newB1 = BitSet()
    newB1.or(this)
    newB1.xor(other)
    return newB1
}

operator fun BitSet.minusAssign(other: BitSet) {
    this.xor(other)
}

fun BitSet.orWithOffset(other: BitSet, offset: Int = 0) {
    val newB1 = BitSet()
    for(i in 0 until other.length()) {
        newB1.set(i+offset, other[i])
    }
    or(newB1)
}


fun BitSet.xorWithOffset(other: BitSet, offset: Int = 0) {
    val newB1 = BitSet()
    for(i in 0 until other.length()) {
        newB1.set(i+offset, other[i])
    }
    xor(newB1)
}
