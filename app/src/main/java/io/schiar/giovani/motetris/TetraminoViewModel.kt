package io.schiar.giovani.motetris

import androidx.lifecycle.MutableLiveData

class TetraminoViewModel {
    val tetraminoFetcher = TetraminoFetcher()

    private var state: Int = 0

    val blocks: MutableLiveData<List<BlockViewModel>> by lazy {
        MutableLiveData<List<BlockViewModel>>()
    }

    val type: MutableLiveData<Char> by lazy {
        MutableLiveData<Char>()
    }

    val topMargin: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val leftMargin: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun moveLeft(step: Int) {
        val blocks = blocks.value ?: return
        blocks.map {
            it.moveLeft(step)
        }
    }

    fun moveRight(step: Int) {
        val blocks = blocks.value ?: return
        blocks.map {
            it.moveRight(step)
        }
    }

    fun moveDown(step: Int) {
        val blocks = blocks.value ?: return
        blocks.map {
            it.moveDown(step)
        }
    }

    fun rotateLeft(blockSize: Int) {
        val blocks = blocks.value ?: return
        val type = type.value ?: return

        when (type) {
            'i' -> {
                state = (state + 1) % 2
                when (state) {
                    1 -> rotateTetraminoIFromState0ToState1(blockSize, blocks)
                    0 -> rotateTetraminoIFromState1ToState0(blockSize, blocks)
                }
            }
            'l' -> {
                state = (state + 1) % 4
                when (state) {
                    1 -> rotateTetraminoLFromState0ToState1(blockSize, blocks)
                    2 -> rotateTetraminoLFromState1ToState2(blockSize, blocks)
                    3 -> rotateTetraminoLFromState2ToState3(blockSize, blocks)
                    0 -> rotateTetraminoLFromState3ToState0(blockSize, blocks)
                }
            }
            'o' -> return
            's' -> {
                state = (state + 1) % 2
                when (state) {
                    1 -> rotateTetraminoSFromState0ToState1(blockSize, blocks)
                    0 -> rotateTetraminoSFromState1ToState0(blockSize, blocks)
                }
            }
            't' -> {
                state = (state + 1) % 4
                when (state) {
                    1 -> rotateTetraminoTFromState0ToState1(blockSize, blocks)
                    2 -> rotateTetraminoTFromState1ToState2(blockSize, blocks)
                    3 -> rotateTetraminoTFromState2ToState3(blockSize, blocks)
                    0 -> rotateTetraminoTFromState3ToState0(blockSize, blocks)
                }
            }
            else -> return
        }
    }

//    fun rotateRight(blockSize: Float) {
//        val blocks = blocks.value ?: return
//        val type = type.value ?: return
//
//        when (type) {
//            'i' -> {
//                state = (state - 1) % 2
//                when (state) {
//                    1 -> rotateTetraminoIFromState1ToState0(blockSize, blocks)
//                    0 -> rotateTetraminoIFromState0ToState1(blockSize, blocks)
//                }
//            }
//            'l' -> {
//                state = (state - 1) % 4
//                when (state) {
//                    1 -> rotateTetraminoLFromState1ToState0(blockSize, blocks)
//                    2 -> rotateTetraminoLFromState2ToState1(blockSize, blocks)
//                    3 -> rotateTetraminoLFromState3ToState2(blockSize, blocks)
//                    0 -> rotateTetraminoLFromState0ToState3(blockSize, blocks)
//                }
//            }
//            'o' -> return
//            's' -> {
//                state = (state - 1) % 2
//                when (state) {
//                    1 -> rotateTetraminoSFromState1ToState0(blockSize, blocks)
//                    0 -> rotateTetraminoSFromState0ToState1(blockSize, blocks)
//                }
//            }
//            't' -> {
//                state = (state - 1) % 4
//                when (state) {
//                    1 -> rotateTetraminoTFromState1ToState0(blockSize, blocks)
//                    2 -> rotateTetraminoTFromState2ToState1(blockSize, blocks)
//                    3 -> rotateTetraminoTFromState3ToState2(blockSize, blocks)
//                    0 -> rotateTetraminoTFromState0ToState3(blockSize, blocks)
//                }
//            }
//            else -> return
//        }
//    }

    fun nextTetramino(blockSize: Int, initialLeftMargin: Int) {
        val tetramino = tetraminoFetcher.nextTetramino()
        createBlocks(blockSize, 0, initialLeftMargin, tetramino.type)
    }

    fun nextType(): Char {
        return tetraminoFetcher.next()
    }

    private fun createBlocks(blockSize: Int, topMargin: Int, leftMargin: Int, type: Char) {
        val newBlockList = initializeBlocks(topMargin, leftMargin, blockSize)
        val newBlocks = when (type) {
            'i' -> createTetraminoI(blockSize, newBlockList)
            'l' -> createTetraminoL(blockSize, newBlockList)
            'o' -> createTetraminoO(blockSize, newBlockList)
            's' -> createTetraminoS(blockSize, newBlockList)
            't' -> createTetraminoT(blockSize, newBlockList)
            else -> return
        }

        blocks.postValue(newBlocks)
        this.type.postValue(type)
        this.topMargin.postValue(topMargin)
        this.leftMargin.postValue(leftMargin)
        state = 0
    }

    private fun initializeBlocks(topMargin: Int, leftMargin: Int, blockSize: Int): List<BlockViewModel> {
        val block1 = BlockViewModel().apply {
            this.topMargin.value = topMargin
            this.leftMargin.value = leftMargin
            this.size.value = blockSize
        }
        val block2 = BlockViewModel().apply {
            this.topMargin.value = topMargin
            this.leftMargin.value = leftMargin
            this.size.value = blockSize
        }
        val block3 = BlockViewModel().apply {
            this.topMargin.value = topMargin
            this.leftMargin.value = leftMargin
            this.size.value = blockSize
        }
        val block4 = BlockViewModel().apply {
            this.topMargin.value = topMargin
            this.leftMargin.value = leftMargin
            this.size.value = blockSize
        }
        return listOf(block1, block2, block3, block4)
    }

    private fun createTetraminoI(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[1].moveDown(blockSize)
        newBlockList[2].moveDown(blockSize*2)
        newBlockList[3].moveDown(blockSize*3)
        return newBlockList
    }

    private fun rotateTetraminoIFromState0ToState1(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[1].moveUp(blockSize)
        newBlockList[1].moveRight(blockSize)
        newBlockList[2].moveUp(blockSize*2)
        newBlockList[2].moveRight(blockSize*2)
        newBlockList[3].moveUp(blockSize*3)
        newBlockList[3].moveRight(blockSize*3)
        return newBlockList
    }

    private fun rotateTetraminoIFromState1ToState0(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[1].moveDown(blockSize)
        newBlockList[1].moveLeft(blockSize)
        newBlockList[2].moveDown(blockSize*2)
        newBlockList[2].moveLeft(blockSize*2)
        newBlockList[3].moveDown(blockSize*3)
        newBlockList[3].moveLeft(blockSize*3)
        return newBlockList
    }

    private fun createTetraminoO(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[1].moveRight(blockSize)
        newBlockList[2].moveDown(blockSize)
        newBlockList[3].moveDown(blockSize)
        newBlockList[3].moveRight(blockSize)
        return newBlockList
    }

    private fun createTetraminoS(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[1].moveDown(blockSize)
        newBlockList[2].moveDown(blockSize)
        newBlockList[2].moveRight(blockSize)
        newBlockList[3].moveDown(blockSize*2)
        newBlockList[3].moveRight(blockSize)
        return newBlockList
    }

    private fun rotateTetraminoSFromState0ToState1(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveDown(blockSize)
        newBlockList[1].moveRight(blockSize)
        newBlockList[2].moveUp(blockSize)
        newBlockList[3].moveUp(blockSize*2)
        newBlockList[3].moveRight(blockSize)
        return newBlockList
    }

    private fun rotateTetraminoSFromState1ToState0(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveUp(blockSize)
        newBlockList[1].moveLeft(blockSize)
        newBlockList[2].moveDown(blockSize)
        newBlockList[3].moveDown(blockSize*2)
        newBlockList[3].moveLeft(blockSize)
        return newBlockList
    }

    private fun createTetraminoL(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[1].moveDown(blockSize)
        newBlockList[2].moveDown(blockSize*2)
        newBlockList[3].moveDown(blockSize*2)
        newBlockList[3].moveRight(blockSize)
        return newBlockList
    }

    private fun rotateTetraminoLFromState0ToState1(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveDown(blockSize*2)
        newBlockList[1].moveDown(blockSize)
        newBlockList[1].moveRight(blockSize)
        newBlockList[2].moveRight(blockSize*2)
        newBlockList[3].moveRight(blockSize)
        newBlockList[3].moveUp(blockSize)
        return newBlockList
    }

    private fun rotateTetraminoLFromState1ToState0(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveUp(blockSize*2)
        newBlockList[1].moveUp(blockSize)
        newBlockList[1].moveLeft(blockSize)
        newBlockList[2].moveLeft(blockSize*2)
        newBlockList[3].moveLeft(blockSize)
        newBlockList[3].moveDown(blockSize)
        return newBlockList
    }

    private fun rotateTetraminoLFromState1ToState2(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveRight(blockSize)
        newBlockList[1].moveUp(blockSize)
        newBlockList[2].moveLeft(blockSize)
        newBlockList[2].moveUp(blockSize*2)
        newBlockList[3].moveUp(blockSize)
        newBlockList[3].moveLeft(blockSize*2)
        return newBlockList
    }

    private fun rotateTetraminoLFromState2ToState1(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveLeft(blockSize)
        newBlockList[1].moveDown(blockSize)
        newBlockList[2].moveRight(blockSize)
        newBlockList[2].moveDown(blockSize*2)
        newBlockList[3].moveDown(blockSize)
        newBlockList[3].moveRight(blockSize*2)
        return newBlockList
    }

    private fun rotateTetraminoLFromState2ToState3(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveUp(blockSize)
        newBlockList[0].moveRight(blockSize)
        newBlockList[2].moveLeft(blockSize)
        newBlockList[2].moveDown(blockSize)
        newBlockList[3].moveDown(blockSize*2)
        return newBlockList
    }

    private fun rotateTetraminoLFromState3ToState2(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveDown(blockSize)
        newBlockList[0].moveLeft(blockSize)
        newBlockList[2].moveRight(blockSize)
        newBlockList[2].moveUp(blockSize)
        newBlockList[3].moveUp(blockSize*2)
        return newBlockList
    }

    private fun rotateTetraminoLFromState3ToState0(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveLeft(blockSize*2)
        newBlockList[0].moveUp(blockSize)
        newBlockList[1].moveLeft(blockSize)
        newBlockList[2].moveDown(blockSize)
        newBlockList[3].moveRight(blockSize)
        return newBlockList
    }

    private fun rotateTetraminoLFromState0ToState3(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveRight(blockSize*2)
        newBlockList[0].moveDown(blockSize)
        newBlockList[1].moveRight(blockSize)
        newBlockList[2].moveUp(blockSize)
        newBlockList[3].moveLeft(blockSize)
        return newBlockList
    }

    private fun createTetraminoT(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[1].moveRight(blockSize)
        newBlockList[2].moveRight(blockSize*2)
        newBlockList[3].moveDown(blockSize)
        newBlockList[3].moveRight(blockSize)
        return newBlockList
    }

    private fun rotateTetraminoTFromState0ToState1(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveDown(blockSize*2)
        newBlockList[1].moveDown(blockSize)
        newBlockList[1].moveLeft(blockSize)
        newBlockList[2].moveLeft(blockSize*2)
        return newBlockList
    }

    private fun rotateTetraminoTFromState1ToState0(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveUp(blockSize*2)
        newBlockList[1].moveUp(blockSize)
        newBlockList[1].moveRight(blockSize)
        newBlockList[2].moveRight(blockSize*2)
        return newBlockList
    }

    private fun rotateTetraminoTFromState1ToState2(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveUp(blockSize)
        newBlockList[0].moveRight(blockSize*2)
        newBlockList[1].moveRight(blockSize)
        newBlockList[2].moveDown(blockSize)
        newBlockList[3].moveUp(blockSize)
        return newBlockList
    }

    private fun rotateTetraminoTFromState2ToState1(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveDown(blockSize)
        newBlockList[0].moveLeft(blockSize*2)
        newBlockList[1].moveLeft(blockSize)
        newBlockList[2].moveUp(blockSize)
        newBlockList[3].moveDown(blockSize)
        return newBlockList
    }

    private fun rotateTetraminoTFromState2ToState3(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveUp(blockSize)
        newBlockList[0].moveLeft(blockSize)
        newBlockList[2].moveRight(blockSize)
        newBlockList[2].moveDown(blockSize)
        newBlockList[3].moveDown(blockSize)
        newBlockList[3].moveLeft(blockSize)
        return newBlockList
    }

    private fun rotateTetraminoTFromState3ToState2(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveDown(blockSize)
        newBlockList[0].moveRight(blockSize)
        newBlockList[2].moveLeft(blockSize)
        newBlockList[2].moveUp(blockSize)
        newBlockList[3].moveUp(blockSize)
        newBlockList[3].moveRight(blockSize)
        return newBlockList
    }

    private fun rotateTetraminoTFromState3ToState0(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveLeft(blockSize)
        newBlockList[1].moveUp(blockSize)
        newBlockList[2].moveUp(blockSize*2)
        newBlockList[2].moveRight(blockSize)
        newBlockList[3].moveRight(blockSize)
        return newBlockList
    }

    private fun rotateTetraminoTFromState0ToState3(blockSize: Int, newBlockList: List<BlockViewModel>): List<BlockViewModel> {
        newBlockList[0].moveRight(blockSize)
        newBlockList[1].moveDown(blockSize)
        newBlockList[2].moveDown(blockSize*2)
        newBlockList[2].moveLeft(blockSize)
        newBlockList[3].moveLeft(blockSize)
        return newBlockList
    }
}