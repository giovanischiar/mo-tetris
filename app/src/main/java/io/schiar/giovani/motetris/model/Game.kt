package io.schiar.giovani.motetris.model

class Game(resolution: Resolution, val sourcePosition: Position) {

    val board: Board = BoardFetcher().fetch(resolution)
    var pause: Boolean = false
    var currentTetramino = TetraminoFetcher().nextTetramino()
    var currentTetraminoPosition = sourcePosition
    var lastTetraminoPosition = sourcePosition

    fun generateTetramino() {
        addTetraminoOnBoard()
    }

    private fun generateNewTetramino() {
        currentTetramino = TetraminoFetcher().nextTetramino()
        currentTetraminoPosition = sourcePosition
        lastTetraminoPosition = sourcePosition
        addTetraminoOnBoard()
    }

    fun start() {
        this.pause = false
    }

    fun pause() {
        this.pause = true
    }

    fun moveTetraminoDown() {
        lastTetraminoPosition = currentTetraminoPosition
        currentTetraminoPosition = Position(currentTetraminoPosition.x, currentTetraminoPosition.y+1)
        updateCurrentTetraminoPosition()
    }

    fun moveTetraminoLeft() {
        lastTetraminoPosition = currentTetraminoPosition
        currentTetraminoPosition = Position(currentTetraminoPosition.x-1, currentTetraminoPosition.y)
        updateCurrentTetraminoPosition(true)
    }

    fun moveTetraminoRight() {
        lastTetraminoPosition = currentTetraminoPosition
        currentTetraminoPosition = Position(currentTetraminoPosition.x+1, currentTetraminoPosition.y)
        updateCurrentTetraminoPosition(true)
    }

    fun rotateTetraminoClockwise() {
        lastTetraminoPosition = currentTetraminoPosition
        removeTetraminoOnLastPosition()
        currentTetramino.shape = TetraminoRotator(currentTetramino).rotateClockWise()
        if (board.bitsetsCollidesBit(currentTetramino.shape, currentTetraminoPosition)) {
            currentTetramino.shape = TetraminoRotator(currentTetramino).rotateAntiClockWise()
        }
        addTetraminoOnBoard()
    }

    private fun updateCurrentTetraminoPosition(sideUpdating: Boolean = false) {
        removeTetraminoOnLastPosition()
        val collides = board.bitsetsCollidesBit(currentTetramino.shape, currentTetraminoPosition)
        addTetraminoOnBoard(collides)
        if (collides && !sideUpdating) {
            generateNewTetramino()
        }
    }

    private fun removeTetraminoOnLastPosition() {
        board.remBitSetsOnBoard(currentTetramino.shape, lastTetraminoPosition)
    }

    private fun addTetraminoOnBoard(collides: Boolean = false) {
        if (collides) {
            currentTetraminoPosition = lastTetraminoPosition
        }

        board.addBitSetsOnBoard(currentTetramino.shape, currentTetraminoPosition)
    }
}