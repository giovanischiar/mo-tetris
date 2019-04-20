package io.schiar.giovani.motetris

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.game_fragment.*
import kotlin.math.ceil


class GameFragment : Fragment() {

    fun onDeviceShake() {
        if (gravityLife.childCount > 0) {
            gravityLife.removeView(gravityLife.children.last())
            updateBlockPositionsThread(listOf(), true)
        }
    }

    private lateinit var viewModel: GameViewModel
    private var tetramino: List<View> = listOf()
    private val handler = Handler()
    private lateinit var runnable: Runnable
    private var step = 0
    private var screenHeight = 0
    private var screenWidth = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    fun createTetramino(blocks: List<BlockViewModel>) {
        tetramino = listOf()
        blocks.map {
            val blockView = layoutInflater.inflate(R.layout.block, null)
            canvas.addView(blockView)
            val blockViewLayoutParams = blockView.layoutParams as RelativeLayout.LayoutParams
            blockViewLayoutParams.leftMargin = it.leftMargin.value ?: return@map
            blockViewLayoutParams.topMargin = it.topMargin.value ?: return@map
            blockViewLayoutParams.width = it.size.value ?: return@map
            blockViewLayoutParams.height = it.size.value ?: return@map
            blockView.layoutParams = blockViewLayoutParams
            tetramino = listOf(blockView, *(tetramino.toTypedArray()))
        }
    }

    fun addNext(type: Char) {
        val tetraminoView = when(type) {
            'i' -> layoutInflater.inflate(R.layout.tetramino_i, null)
            'l' -> layoutInflater.inflate(R.layout.tetramino_l, null)
            'o' -> layoutInflater.inflate(R.layout.tetramino_o, null)
            's' -> layoutInflater.inflate(R.layout.tetramino_s, null)
            't' -> layoutInflater.inflate(R.layout.tetramino_t, null)
            else -> return
        }

        next.addView(tetraminoView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        step = ceil(resources.getDimension(R.dimen.blockSize)).toInt()
        screenWidth = step * 10
        screenHeight = step * 17
        val canvasParams = canvas.layoutParams
        canvasParams.height = screenHeight
        canvasParams.width = screenWidth
        canvas.layoutParams = canvasParams

        val maxBlockPerLine = screenWidth/step
        val initialPos = (step*((maxBlockPerLine)/2))-step
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        viewModel.initTetramino(step, initialPos)
        viewModel.resetScore()

        viewModel.score.observe(this, Observer {
            score.text = it.toString()
        })

        viewModel.nextType.observe(this, Observer {
            next.removeAllViews()
            addNext(it)
        })

        viewModel.currentTetraminoViewModel.observe(this, Observer {tetraminoViewModel ->

            viewModel.nextType.postValue(tetraminoViewModel.nextType())

            tetraminoViewModel.blocks.observe(this, Observer {blocks ->
                createTetramino(blocks)

                blocks.mapIndexed { i, blockViewModel ->
                    blockViewModel.color.observe(this, Observer {
                        tetramino[i].setBackgroundColor(it)
                    })

                    blockViewModel.size.observe(this, Observer {
                        val blockView = tetramino[i]
                        val blockViewParams = blockView.layoutParams as RelativeLayout.LayoutParams
                        blockViewParams.height = it
                        blockViewParams.width = it
                        tetramino[i].layoutParams = blockViewParams
                    })

                    blockViewModel.leftMargin.observe(this, Observer {
                        val blockView = tetramino[i]
                        val blockViewParams = blockView.layoutParams as RelativeLayout.LayoutParams
                        blockViewParams.leftMargin = it
                        tetramino[i].layoutParams = blockViewParams
                    })

                    blockViewModel.topMargin.observe(this, Observer {
                        if (checkStopConditions()) {
                            handler.removeCallbacks(runnable)
                            if (gameOver()) {
                                viewModel.resetScore()
                                Toast.makeText(requireContext(), "Game Over", Toast.LENGTH_LONG).show()
                                canvas.removeAllViews()
                            }
                            checkLinesForFullLine()
                            tetraminoViewModel.nextTetramino(step, initialPos)
                            viewModel.nextType.postValue(tetraminoViewModel.nextType())
                            return@Observer
                        }

                        val blockView = tetramino[i]
                        val blockViewParams = blockView.layoutParams as RelativeLayout.LayoutParams
                        blockViewParams.topMargin = it
                        tetramino[i].layoutParams = blockViewParams
                    })
                }

                start(tetraminoViewModel)

                left_btn.setOnClickListener {
                    tetraminoViewModel.moveLeft(step)
                }

                left_btn.setOnLongClickListener {
                    tetraminoViewModel.moveLeft(step)
                    return@setOnLongClickListener true
                }

                right_btn.setOnClickListener {
                    tetraminoViewModel.moveRight(step)
                }

                right_btn.setOnLongClickListener {
                    tetraminoViewModel.moveRight(step)
                    return@setOnLongClickListener true
                }

                up_btn.setOnClickListener {
                    tetraminoViewModel.rotateLeft(step)
                }

                down_btn.setOnClickListener {
                    tetraminoViewModel.moveDown(step)
                }
            })


        })

    }

    private fun gameOver(): Boolean {
        for (child in canvas.children) {
            if (child.top == 0) {
                return true
            }
        }
        return false
    }

//    private fun checkBlockConditions(): Boolean {
//        tetramino.map {
//            if (reachSideOfScreen(it)) {
//                return true
//            }
//
//            if (checkAllSideCollisions(it)) {
//                return true
//            }
//        }
//        return false
//    }
//
//    private fun reachSideOfScreen(v1: View): Boolean {
//        return v1.left == canvas.layoutParams.width
//    }
//
//    private fun checkAllSideCollisions(v1: View): Boolean {
//        for (view in canvas.children) {
//            if (checkSideCollision(v1, view) && !tetramino.contains(view) && !v1.equals(view)) {
//                return true
//            }
//        }
//        return false
//    }

//    private fun checkSideCollision(v1: View, v2:View): Boolean {
//        val r1 = Rect(v1.left-10, v1.top+10, v1.right+10, v1.bottom-10)
//        val r2 = Rect(v2.left-10, v2.top+10, v2.right+10, v2.bottom-10)
//        return r1.intersect(r2)
//    }


    private fun checkStopConditions(): Boolean {
        tetramino.map {
            if (reachEndOfScreen(it)) {
                return true
            }

            if (checkAllCollisions(it)) {
                return true
            }
        }
        return false
    }

    private fun updateBlockPositions(lineTopMargin: List<Int>, specialBlockUpdate: Boolean): Boolean {
        var hasUpdated = false
        if (specialBlockUpdate) {
            for (block in canvas.children) {
                if (tetramino.contains(block)) {
                    continue
                }

                if (reachEndOfScreen(block)) {
                    continue
                }

                if (checkAllCollisions(block)) {
                    continue
                }
                moveBlockDown(block)
                hasUpdated = true
            }
            return hasUpdated
        }

        for(lineMargin in lineTopMargin) {
            for (block in canvas.children) {
                if (tetramino.contains(block)) {
                    continue
                }
                if (lineMargin > block.top) {
                    if (!checkAllCollisions(block)) {
                        moveBlockDown(block)
                        hasUpdated = true
                    }
                }
            }
        }
        return hasUpdated
    }

    private fun moveBlockDown(block: View) {
        val blockParams = block.layoutParams as RelativeLayout.LayoutParams
        blockParams.topMargin = blockParams.topMargin + step
        canvas.getChildAt(canvas.indexOfChild(block)).layoutParams = blockParams
    }

    private fun checkLinesForFullLine() {
        val linesSize = screenHeight/step
        val maxBlockPerLine = screenWidth/step
        var lineRemoved = false
        var linesRemoved = 0
        var lineTopMargin = listOf<Int>()
        for (i in linesSize downTo 0) {
            var line = listOf<View>()
            for (child in canvas.children) {
                val bottomLine = i * step
                if (child.bottom == bottomLine) {
                    line = listOf(child, *(line.toTypedArray()))
                }
            }
            if (line.size == maxBlockPerLine) {
                viewModel.updateScore()
                linesRemoved++
                lineTopMargin = listOf(line[0].top, *(lineTopMargin.toTypedArray()))
                line.map {
                    canvas.removeView(it)
                }
                lineRemoved = true
            }
        }

        if (lineRemoved) {
            viewModel.updateScore(linesRemoved)
            updateBlockPositionsThread(lineTopMargin)
        }
    }

    private fun updateBlockPositionsThread(lineTopMargin: List<Int>, specialBlockUpdate: Boolean = false) {
        val updaterHandler = Handler()
        var blocksUpdated = false
        val updaterRunnable = object: Runnable {
            override fun run() {
                requireActivity().runOnUiThread {
                    blocksUpdated = updateBlockPositions(lineTopMargin, specialBlockUpdate)
                }
                if (!blocksUpdated) {
                    updaterHandler.removeCallbacks(this)
                    checkLinesForFullLine()
                    return
                }
                updaterHandler.postDelayed(this, 100)
            }
        }
        updaterHandler.postDelayed(updaterRunnable, 100)
    }

    private fun reachEndOfScreen(v1: View): Boolean {
        return v1.bottom == canvas.layoutParams.height
    }

    private fun checkAllCollisions(v1: View): Boolean {
        for (view in canvas.children) {
            if (checkCollision(v1, view) && !tetramino.contains(view) && !v1.equals(view)) {
                return true
            }
        }
        return false
    }

    private fun checkCollision(v1: View, v2:View): Boolean {
        val r1 = Rect(v1.left+10, v1.top, v1.right-10, v1.bottom+10)
        val r2 = Rect(v2.left+10, v2.top, v2.right-10, v2.bottom-10)
        return r1.intersect(r2)
    }

    private fun start(tetraminoViewModel: TetraminoViewModel) {
        runnable = object: Runnable {
            override fun run() {
                tetraminoViewModel.moveDown(step)
                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(runnable, 1000)
    }
}
