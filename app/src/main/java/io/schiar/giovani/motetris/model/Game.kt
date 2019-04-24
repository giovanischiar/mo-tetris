package io.schiar.giovani.motetris.model

import io.schiar.giovani.motetris.OnChangeGameListener
import io.schiar.giovani.motetris.OnInputListener

class Game(resolution: Resolution, private val sourcePosition: Position, private val onChangeGameListener: OnChangeGameListener): Runnable, OnInputListener {

    val board: Board = BoardFetcher().fetch(resolution)
    val tetraminoFetcher = TetraminoFetcher()
    var currentTetramino = tetraminoFetcher.nextTetramino()
    var currentTetraminoPosition = sourcePosition
    var lastTetraminoPosition = sourcePosition

    override fun run() {
        while (!Thread.currentThread().isInterrupted) {
            try {
                moveTetraminoDown()
                Thread.sleep(1000)
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    private fun generateNewTetramino() {
        currentTetramino = tetraminoFetcher.nextTetramino()
        currentTetraminoPosition = sourcePosition
        lastTetraminoPosition = sourcePosition
        addTetraminoOnBoard()
    }

    override fun moveTetraminoDown() {
        lastTetraminoPosition = currentTetraminoPosition
        currentTetraminoPosition = Position(currentTetraminoPosition.x, currentTetraminoPosition.y+1)
        updateCurrentTetraminoPosition()
    }

    override fun moveTetraminoLeft() {
        lastTetraminoPosition = currentTetraminoPosition
        currentTetraminoPosition = Position(currentTetraminoPosition.x-1, currentTetraminoPosition.y)
        updateCurrentTetraminoPosition(true)
    }

    override fun moveTetraminoRight() {
        lastTetraminoPosition = currentTetraminoPosition
        currentTetraminoPosition = Position(currentTetraminoPosition.x+1, currentTetraminoPosition.y)
        updateCurrentTetraminoPosition(true)
    }

    override fun rotateTetraminoClockwise() {
        lastTetraminoPosition = currentTetraminoPosition
        removeTetraminoOnLastPosition()
        currentTetramino.shape = TetraminoRotator(currentTetramino).rotateClockWise()
        if (board.bitsetsCollidesBit(currentTetramino.shape, currentTetraminoPosition)) {
            currentTetramino.shape = TetraminoRotator(currentTetramino).rotateAntiClockWise()
        }
        addTetraminoOnBoard()
        onChangeGameListener.updateGameState(this)
    }

    private fun updateCurrentTetraminoPosition(sideUpdating: Boolean = false) {
        removeTetraminoOnLastPosition()
        val collides = board.bitsetsCollidesBit(currentTetramino.shape, currentTetraminoPosition)
        addTetraminoOnBoard(collides)
        if (collides && !sideUpdating) {
            board.removeFullLinesAndUpdateBoard()
            onChangeGameListener.updateScore(board.linesRemoved)
            generateNewTetramino()
        }
        onChangeGameListener.updateGameState(this)
        onChangeGameListener.updateNextTetramino(Tetramino(tetraminoFetcher.next()).shape)
    }

    private fun removeTetraminoOnLastPosition() {
        board.remBitSetsOnBoard(currentTetramino.shape, lastTetraminoPosition)
    }

    fun addTetraminoOnBoard(collides: Boolean = false) {
        if (collides) {
            currentTetraminoPosition = lastTetraminoPosition
        }

        board.addBitSetsOnBoard(currentTetramino.shape, currentTetraminoPosition)
    }
}