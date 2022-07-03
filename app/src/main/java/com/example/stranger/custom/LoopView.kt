package com.example.stranger.custom


import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.stranger.R


class LoopView : View {
    private var textSize: Int = 0
    private val DEFAULT_TEXT_SIZE = 13
    private lateinit var flingGestureDetector: GestureDetector

    constructor(context: Context?) : super(context) {
        init(context, null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context?, attrs: AttributeSet?) {
        val typedArray: TypedArray? =
            context?.obtainStyledAttributes(attrs, R.styleable.LoopView)
        flingGestureDetector = GestureDetector(context, object :
            GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {

                return super.onFling(e1, e2, velocityX, velocityY)
            }
        })
        flingGestureDetector.setIsLongpressEnabled(false)

        if (typedArray != null) {
            textSize = typedArray.getInteger(R.styleable.LoopView[0], DEFAULT_TEXT_SIZE)
            textSize = (Resources.getSystem().displayMetrics.density * textSize) as Int

        }

    }

}

