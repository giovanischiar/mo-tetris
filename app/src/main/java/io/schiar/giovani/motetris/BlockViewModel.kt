package io.schiar.giovani.motetris

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BlockViewModel : ViewModel() {
    val size: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val topMargin: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val leftMargin: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun moveLeft(step: Int) {
        val oldLeftMargin = leftMargin.value ?: return
        leftMargin.postValue(oldLeftMargin - step)
    }

    fun moveRight(step: Int) {
        val oldLeftMargin = leftMargin.value ?: return
        leftMargin.postValue(oldLeftMargin + step)
    }

    fun moveDown(step: Int) {
        val oldTopMargin = topMargin.value ?: return
        topMargin.postValue(oldTopMargin + step)
    }

    fun moveUp(step: Int) {
        val oldTopMargin = topMargin.value ?: return
        topMargin.postValue(oldTopMargin - step)
    }
}
