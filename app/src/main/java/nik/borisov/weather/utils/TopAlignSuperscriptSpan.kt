package nik.borisov.weather.utils

import android.text.TextPaint
import android.text.style.SuperscriptSpan

class TopAlignSuperscriptSpan : SuperscriptSpan() {

    private val fontScale = 2

    override fun updateDrawState(textPaint: TextPaint) {
        val ascent = textPaint.ascent()
        textPaint.textSize = textPaint.textSize / fontScale
        textPaint.baselineShift += (ascent / 2.5).toInt()
    }

    override fun updateMeasureState(textPaint: TextPaint) {
        updateDrawState(textPaint)
    }

}