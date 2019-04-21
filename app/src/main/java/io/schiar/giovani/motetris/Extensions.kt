package io.schiar.giovani.motetris

import java.util.*

fun <E> Iterable<E>.updated(index: Int, elem: E) = mapIndexed {
        i, existing ->  if (i == index) elem else existing
}

fun BitSet.withTrueSize(trueSize: Int): BitSet {
    this.set(trueSize)
    return this
}

fun BitSet.trueSize(): Int {
    return this.length()-1
}

fun BitSet.truePart(): BitSet {
    return this.get(0, this.trueSize())
}
