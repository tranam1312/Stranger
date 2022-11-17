/*
 *  Created by Trần Nam on 11/12/22, 1:18 AM
 *    tranhoainam1312@gmail.com
 *     Last modified 11/12/22, 1:18 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.stranger.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.view.View


class CustomCircularProgressDrawable() :Drawable(), Animatable{

    // Default progress animation colors are grays.
    private val COLOR1 = -0x4d000000
    private val COLOR2 = -0x80000000
    private val COLOR3 = 0x4d000000
    private val COLOR4 = 0x1a000000

    // The duration of the animation cycle.
    private val ANIMATION_DURATION_MS = 2000

    // The duration of the animation to clear the bar.
    private val FINISH_ANIMATION_DURATION_MS = 1000

    // Interpolator for varying the speed of the animation.

    private val mPaint = Paint()
    private val mClipRect = RectF()
    private val mTriggerPercentage = 0f
    private val mStartTime: Long = 0
    private val mFinishTime: Long = 0
    private val mRunning = false

    // Colors used when rendering the animation,
    private val mColor1 = 0
    private val mColor2 = 0
    private val mColor3 = 0
    private val mColor4 = 0
    private val mParent: View? = null

    override fun draw(canvas: Canvas) {
        val radius = 50.0f
        canvas.drawCircle(radius, radius, radius, mPaint!!)
    }

    override fun setAlpha(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun setColorFilter(p0: ColorFilter?) {
    }

    override fun getOpacity(): Int {
        TODO("Not yet implemented")
    }

    override fun start() {

    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun isRunning(): Boolean {
        TODO("Not yet implemented")
    }
}