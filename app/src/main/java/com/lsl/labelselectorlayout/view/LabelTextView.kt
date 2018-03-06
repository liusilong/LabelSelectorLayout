package com.lsl.labelselectorlayout.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView

/**
 * Created by liusilong on 2018/3/6.
 * version:1.0
 * Describe:
 */
class LabelTextView(context: Context, attr: AttributeSet?)
    : TextView(context, attr) {

    private val paint = Paint().apply {
        this.isAntiAlias = true
        this.style = Paint.Style.STROKE
        this.strokeWidth = 5.0f
        this.color = Color.GRAY
    }
    private var hasSelected = false

    constructor(context: Context) : this(context, null) {
        this.gravity = Gravity.CENTER
        setTextColor(Color.BLACK)
    }

    override fun onDraw(canvas: Canvas?) {
        if (hasSelected) {
            paint.style = Paint.Style.FILL
            setTextColor(Color.WHITE)
            paint.color = Color.GRAY
            canvas?.apply {
                this.drawRoundRect(10f,
                        10f,
                        width.toFloat() - 10,
                        height.toFloat() - 10,
                        10.0f,
                        10.0f,
                        paint)
            }
        } else {
            paint.style = Paint.Style.STROKE
            setTextColor(Color.BLACK)
            canvas?.apply {
                this.drawRoundRect(10f,
                        10f,
                        width.toFloat() - 10,
                        height.toFloat() - 10,
                        10.0f,
                        10.0f,
                        paint)
            }
        }
        super.onDraw(canvas)
    }

    fun changeStatus(isSelected: Boolean) {
        this.hasSelected = isSelected
        invalidate()
    }

    fun hasSelected() = hasSelected
}