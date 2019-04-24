package io.schiar.giovani.motetris

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_game.*
import java.util.*
import kotlin.math.ceil

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private var pixelSize = 0
    private val pixelsWidthCount = 10
    private val pixelsHeightCount = 17

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pixelSize = ceil(resources.getDimension(R.dimen.blockSize)).toInt()
        setupViewPort()

        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        viewModel.bitSets.observe(this, Observer { onViewPortChanged(it) })
        viewModel.nextContent.observe(this, Observer { onNextChanged(it) })
        viewModel.score.observe(this, Observer { onScoreChanged(it) })

        left_btn.setOnClickListener { onLeftButtonClicked() }
        right_btn.setOnClickListener { onRightButtonClicked() }
        down_btn.setOnClickListener { onDownButtonClicked() }
        up_btn.setOnClickListener { onUpButtonClicked() }

        viewModel.startGame((pixelsWidthCount/2)-1, 0, pixelsWidthCount, pixelsHeightCount)
    }

    private fun setupViewPort() {
        val paintAreaParams = viewport.layoutParams as RelativeLayout.LayoutParams
        paintAreaParams.width = pixelsWidthCount * pixelSize
        paintAreaParams.height = pixelsHeightCount * pixelSize
        viewport.layoutParams = paintAreaParams
    }

    private fun onScoreChanged(score: String) {
        this.score.text = String.format(resources.getString(R.string.score), score)
    }

    private fun onNextChanged(tetraminoShape: List<BitSet>) {
        drawBitSets(next, tetraminoShape)
    }

    private fun onViewPortChanged(bitSets: List<BitSet>) {
        drawBitSets(viewport, bitSets)
    }

    private fun drawBitSets(layout: RelativeLayout, bitSets: List<BitSet>) {
        layout.removeAllViews()
        for ((i, bitSet) in bitSets.withIndex()) {
            for (j in (0..bitSet.length())) {
                if (bitSet[j]) {
                    val block = layoutInflater.inflate(R.layout.block, layout, false)
                    layout.addView(block)
                    val blockParams = block.layoutParams as RelativeLayout.LayoutParams
                    blockParams.apply {
                        leftMargin = pixelSize * j
                        topMargin = pixelSize * i
                        width = pixelSize
                        height = pixelSize
                    }
                    block.layoutParams = blockParams
                }
            }
        }
    }

    private fun onLeftButtonClicked() {
        viewModel.leftClicked()
    }

    private fun onRightButtonClicked() {
        viewModel.rightClicked()
    }

    private fun onDownButtonClicked() {
        viewModel.downClicked()
    }

    private fun onUpButtonClicked() {
        viewModel.upClicked()
    }

}