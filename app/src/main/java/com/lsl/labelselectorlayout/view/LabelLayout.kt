package com.lsl.labelselectorlayout.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup

/**
 * Created by liusilong on 2018/3/6.
 * version:1.0
 * Describe:
 */
class LabelLayout(context: Context, attr: AttributeSet?) : ViewGroup(context, attr) {

    constructor(context: Context) : this(context, null)

    private val screenWidth: Int by lazy {
        resources.displayMetrics.widthPixels
    }
    private val MAX_TWO = 2
    private val MAX_THREE = 3


    private val selectedTextList = mutableListOf<String>()


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val count = childCount
//        当前行号 0,1,2,3,4
        var currRowId = 0
//        当前行的 Item 数量
        var currRowItemCount = 0

        var width = 0
        var height = 0

        var currentHeight = 0
        var currentWidth = 0


        for (i in 0 until count) {
            val child = getChildAt(i)
//            测量子 View
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
//            每行的偏移量
            var offset: Int
            offset = if (currRowId % 2 == 0) {
                (screenWidth - (child.measuredWidth * 2)) / 2
            } else {
                (screenWidth - (child.measuredWidth * 3)) / 2
            }
            val lp = child.layoutParams as MyLayoutParams
            lp.x = currentWidth + offset
            lp.y = height
            currentHeight = Math.max(currentHeight, child.measuredHeight)
            currentWidth += child.measuredWidth + lp.leftMargin + lp.rightMargin

//           一行两个
            if (currRowId % 2 == 0 && currRowItemCount <= MAX_TWO) {
                currRowItemCount++
                if (currRowItemCount == MAX_TWO) {
                    currRowId++
                    height += currentHeight + lp.topMargin + lp.bottomMargin
                    width = Math.max(width, currentWidth)
                    currRowItemCount = 0
                    currentWidth = 0
                    currentHeight = 0
                }
            } else { //一行3个
                currRowItemCount++
                if (currRowItemCount == MAX_THREE) {
                    currRowId++
                    height += currentHeight + lp.topMargin + lp.bottomMargin
                    width = Math.max(width, currentWidth)
                    currRowItemCount = 0
                    currentWidth = 0
                    currentHeight = 0
                }
            }
        }//for end

        setMeasuredDimension(View.resolveSize(width, widthMeasureSpec),
                View.resolveSize(height, heightMeasureSpec))

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            val lp = child.layoutParams as MyLayoutParams
//            Log.e("onLayout", "第 $i 个 View x = ${lp.x},y = ${lp.y}")
            Log.e("onLayout", "第 $i 个 margins:l = ${lp.leftMargin},t = ${lp.topMargin}, r = ${lp.rightMargin}, b = ${lp.bottomMargin}")
            child.layout(
                    lp.x,
                    lp.y,
                    lp.x + child.measuredWidth,
                    lp.y + child.measuredHeight
            )
        }
    }

    /**
     * 设置列表
     */
    fun setList(items: List<String>) {
        items.forEach {
            it.apply {
                val lp = MyLayoutParams(400, 160)
                lp.setMargins(16, 16, 16, 16)
                val textView = LabelTextView(this@LabelLayout.context)
                textView.layoutParams = lp
                textView.text = this
                this@LabelLayout.addView(textView)

                textView.apply {
                    this.setOnClickListener {
                        if (this.hasSelected()) {
                            changeStatus(false)
                            if (selectedTextList.contains(this.text.toString()))
                                selectedTextList.remove(this.text.toString())
                        } else {
                            changeStatus(true)
                            selectedTextList.add(this.text.toString())
                        }

                        selectedTextList.forEach { Log.e("selectedText", it) }
                    }
                }
            }
        }
    }

    /**
     * 获取选中的列表
     */
    fun getSelectedList() = selectedTextList

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is MyLayoutParams
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return MyLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MyLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MyLayoutParams(p!!.width, p.height)

    }

    class MyLayoutParams : ViewGroup.MarginLayoutParams {
        var x: Int = 0
        var y: Int = 0

        constructor(context: Context, attr: AttributeSet?) : super(context, attr)

        constructor(width: Int, height: Int) : super(width, height)

    }

}