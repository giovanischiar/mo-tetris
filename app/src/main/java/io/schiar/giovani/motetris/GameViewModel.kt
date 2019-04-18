package io.schiar.giovani.motetris

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class GameViewModel : ViewModel() {
    val score: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val currentTetraminoViewModel: MutableLiveData<TetraminoViewModel> by lazy {
        MutableLiveData<TetraminoViewModel>()
    }

    fun nextTetramino(blockSize: Int, initialLeftMargin: Int) {
        val nextTetraminoViewModel = TetraminoViewModel().apply {
            this.nextTetramino(blockSize, initialLeftMargin)
        }
        currentTetraminoViewModel.value = nextTetraminoViewModel
    }

    fun updateScore() {
        var newScore = this.score.value ?: 0
        this.score.postValue(++newScore)
    }

    fun updateScore(scoreToAdd: Int) {
        var newScore = this.score.value ?: 0
        newScore += scoreToAdd
        this.score.postValue(newScore)
    }

    fun resetScore() {
        this.score.postValue(0)
    }
}
