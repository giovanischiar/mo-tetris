package io.schiar.giovani.motetris

import android.os.Handler
import android.os.HandlerThread
import io.schiar.giovani.motetris.model.Game

class Engine(private val game: Game): OnNextStateListener {

    private val handlerThread = HandlerThread("engine thread").apply{ start() }
    private val handler = Handler(handlerThread.looper)

    fun start() {
        game.onNextMoveListener = this
        handler.post(game)
    }

    fun runOnEngine(function: () -> Unit) {
        handler.post(function)
    }

    override fun nextState() {
        handler.postDelayed(game, 1000)
    }

}