package io.schiar.giovani.motetris.model

import io.schiar.giovani.motetris.OnChangeGameListener
import io.schiar.giovani.motetris.OnInputListener

class Game(
    resolution: Resolution,
    tetraminoColors: Map<TetraminoTypes, Int>,
    private val sourcePosition: Position,
    private val onChangeGameListener: OnChangeGameListener
): Runnable, OnInputListener {

    val board: Board = BoardFetcher().fetch(resolution)

    private val tetraminoFetcher = TetraminoFetcher(tetraminoColors)
    private var currentTetramino = tetraminoFetcher.nextTetramino()
    private var currentTetraminoPosition = sourcePosition
    private var lastTetraminoPosition = sourcePosition

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
        currentTetramino.shape = TetraminoFlipper(currentTetramino).rotateClockWise()
        if (board.verifyColorBitSetsCollision(currentTetramino.shape, currentTetraminoPosition)) {
            currentTetramino.shape = TetraminoFlipper(currentTetramino).rotateAntiClockWise()
        }
        addTetraminoOnBoard()
        onChangeGameListener.updateGameState(this)
    }

    private fun updateCurrentTetraminoPosition(sideUpdating: Boolean = false) {
        removeTetraminoOnLastPosition()
        val collides = board.verifyColorBitSetsCollision(currentTetramino.shape, currentTetraminoPosition)
        addTetraminoOnBoard(collides)
        if (collides && !sideUpdating) {
            board.removeFullLinesAndUpdateBoard()
            onChangeGameListener.updateScore(board.linesRemoved.toString())
            generateNewTetramino()
        }
        onChangeGameListener.updateGameState(this)
        // onChangeGameListener.updateNextTetramino(tetraminoFetcher.next().shape)
    }

    private fun removeTetraminoOnLastPosition() {
        val tetramino = currentTetramino
        board.remColorBitsOnBoard(tetramino.shape, lastTetraminoPosition)
    }

    fun addTetraminoOnBoard(collides: Boolean = false) {
        if (collides) {
            currentTetraminoPosition = lastTetraminoPosition
        }

        val tetramino = currentTetramino
        board.addColorBitsOnBoard(tetramino.shape, currentTetraminoPosition)
    }

}