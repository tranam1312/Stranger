package com.example.stranger.base

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.stranger.R
import com.example.stranger.databinding.ToolbarBinding

class BaseHeader : ConstraintLayout {
    private var binding: ToolbarBinding? = null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView()
    }

    private fun initView() {
        binding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.toolbar, this, false)
        addView(binding?.root)
    }

    fun setTitle(title: String) {
        endTitleAnimation()
        binding?.title?.text = title
        startTitleAnia()
    }

    fun startTitleAnia() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.text_view_tranlate_end).apply {
            duration = 1000
            start()
        }
        binding?.title?.startAnimation(animation)
    }
    fun endTitleAnimation(){
        val animation = AnimationUtils.loadAnimation(context, R.anim.text_view_tranlate_strat).apply {
            duration = 1000
            start()
        }
        binding?.title?.startAnimation(animation)
    }
}