package io.schiar.giovani.motetris

import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.schiar.giovani.motetris.util.ColorBit
import kotlin.math.ceil

object BindingAdapters {

    @BindingAdapter("score")
    @JvmStatic
    fun setScore(view: TextView, text: String) {
        view.text = String.format(view.resources.getString(R.string.score), text)
    }

    @BindingAdapter("layout_width")
    @JvmStatic
    fun setResolutionWidth(view: View, width: Int?) {
        val pixelsWidthCount = width ?: 0
        val pixelSize = ceil(view.resources.getDimension(R.dimen.blockSize)).toInt()
        val paintAreaParams = view.layoutParams as RelativeLayout.LayoutParams
        paintAreaParams.width = pixelsWidthCount * pixelSize
        view.layoutParams = paintAreaParams
    }

    @BindingAdapter("layout_height")
    @JvmStatic
    fun setResolutionHeight(view: View, height: Int?) {
        val pixelsHeightCount = height ?: 0
        val pixelSize = ceil(view.resources.getDimension(R.dimen.blockSize)).toInt()
        val paintAreaParams = view.layoutParams as RelativeLayout.LayoutParams
        paintAreaParams.height = pixelsHeightCount * pixelSize
        view.layoutParams = paintAreaParams
    }

    @BindingAdapter("color_bit_sets")
    @JvmStatic
    fun setColorBitSets(layout: RelativeLayout, optColorBitSets: List<Set<ColorBit>>?) {
        val colorBitSets = optColorBitSets ?: return
        val pixelSize = ceil(layout.resources.getDimension(R.dimen.blockSize)).toInt()
        layout.removeAllViews()
        for ((y, lineColorBitSet) in colorBitSets.withIndex()) {
            val colorBitIterator = lineColorBitSet.iterator()
            while (colorBitIterator.hasNext()) {
                val colorBit = colorBitIterator.next()
                val x = colorBit.getIndex()
                val block = LayoutInflater.from(layout.context).inflate(R.layout.block, layout, false)
                layout.addView(block)
                val blockParams = block.layoutParams as RelativeLayout.LayoutParams
                blockParams.apply {
                    leftMargin = pixelSize * x
                    topMargin = pixelSize * y
                    width = pixelSize
                    height = pixelSize
                }
                block.setBackgroundColor(colorBit.getColor())
                block.layoutParams = blockParams
            }
        }
    }

}

