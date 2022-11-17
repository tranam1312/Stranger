/*
 *  Created by Trần Nam on 11/12/22, 9:48 AM
 *    tranhoainam1312@gmail.com
 *     Last modified 11/12/22, 9:48 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.stranger.base

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class PianoView : View {

    private var whites : ArrayList<Key> = arrayListOf()
    private var blacks : ArrayList<Key> = arrayListOf()
    private var blackPen : Paint = Paint()
    private var whitePen : Paint = Paint()
    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    fun initView() {
        whitePen.color= Color.WHITE
        whitePen.style = Paint.Style.FILL
        blackPen.color = Color.BLACK
        blackPen.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }
}