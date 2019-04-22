package io.schiar.giovani.motetris.model

class Game(resolution: Resolution, val sourcePosition: Position) {

    val board: Board = BoardFetcher().fetch(resolution)
    var pause: Boolean = false
    var currentTetramino = TetraminoFetcher().nextTetramino()
    var currentTetraminoPosition = sourcePosition
    var lastTetraminoPosition = sourcePosition
    var currentRearEndTetraminoPosition = currentTetramino.rearEndPosition(sourcePosition)

    fun generateTetramino() {
        addTetraminoToSourcePositionOnBoard()
    }

    private fun generateNewTetramino() {
        currentTetramino = TetraminoFetcher().nextTetramino()
        currentTetraminoPosition = sourcePosition
        lastTetraminoPosition = sourcePosition
        addTetraminoToSourcePositionOnBoard()
    }

    fun pause() {
        this.pause = true
    }

    fun moveCurrentTetraminoDown() {
        currentTetraminoPosition = Position(currentTetraminoPosition.x, currentTetraminoPosition.y+1)
        lastTetraminoPosition = Position(currentTetraminoPosition.x, currentTetraminoPosition.y-1)
        updateCurrentTetraminoPosition()
    }

    fun moveCurrentTetraminoLeft() {
        currentTetraminoPosition = Position(currentTetraminoPosition.x-1, currentTetraminoPosition.y)
        lastTetraminoPosition = Position(currentTetraminoPosition.x+1, currentTetraminoPosition.y)
        updateCurrentTetraminoPosition(true)
    }

    fun moveCurrentTetraminoRight() {
        currentTetraminoPosition = Position(currentTetraminoPosition.x+1, currentTetraminoPosition.y)
        lastTetraminoPosition = Position(currentTetraminoPosition.x-1, currentTetraminoPosition.y)
        updateCurrentTetraminoPosition(true)
    }

    private fun updateCurrentTetraminoPosition(sideUpdating: Boolean = false) {
        removePreviousTetraminoPosition()
        val collides = board.bitsetsCollidesBit(currentTetramino.shape, currentTetraminoPosition)
        addTetraminoToSourcePositionOnBoard(collides)
        if (collides && !sideUpdating) {
            generateNewTetramino()
        }
    }

    private fun removePreviousTetraminoPosition() {
        board.remBitSetsOnBoard(currentTetramino.shape, lastTetraminoPosition)
    }

    private fun addTetraminoToSourcePositionOnBoard(last: Boolean = false) {
        if (last) {
            currentTetraminoPosition = lastTetraminoPosition
        }
        board.addBitSetsOnBoard(currentTetramino.shape, currentTetraminoPosition)
    }
}