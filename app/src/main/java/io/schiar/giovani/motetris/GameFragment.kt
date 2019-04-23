package io.schiar.giovani.motetris

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.game_fragment.*
import java.util.*
import kotlin.math.ceil

class GameFragment : Fragment() {
    private lateinit var viewModel: GameViewModel
    private var pixelSize = 0
    private val pixelsWidthCount = 10
    private val pixelsHeightCount = 17

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        pixelSize = ceil(resources.getDimension(R.dimen.blockSize)).toInt()
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        val paintAreaParams = viewPort.layoutParams as RelativeLayout.LayoutParams
        paintAreaParams.width = pixelsWidthCount * pixelSize
        paintAreaParams.height = pixelsHeightCount * pixelSize
        viewPort.layoutParams = paintAreaParams

        viewModel.bitSets.observe(this, Observer { onBoardChanged(it) })

        left_btn.setOnClickListener { onLeftButtonClicked() }
        right_btn.setOnClickListener { onRightButtonClicked() }
        down_btn.setOnClickListener { onDownButtonClicked() }
        up_btn.setOnClickListener { onUpButtonClicked() }

        viewModel.startGame((pixelsWidthCount/2)-1, 0, pixelsWidthCount, pixelsHeightCount)
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

    private fun onBoardChanged(bitSets: List<BitSet>) {
        viewPort.removeAllViews()
        for ((i, bitSet) in bitSets.withIndex()) {
            for (j in (0..bitSet.length())) {
                if (bitSet[j]) {
                    val block = layoutInflater.inflate(R.layout.block, null)
                    viewPort.addView(block)
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
}